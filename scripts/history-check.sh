#!/bin/bash

set -e

echo "🔍 Starting Git history analysis..."

commits=($(git rev-list HEAD --reverse))

working=""
regression=""

mkdir -p build_cache

prepare_checkout() {
  git reset --hard --quiet
  git clean -fdx --quiet
}

for commit in "${commits[@]}"; do
  echo "⏳ Checking commit: $commit"
  prepare_checkout
  git checkout "$commit" --quiet

  mvn clean package -q
  if [ $? -eq 0 ]; then
    echo "✅ Build passed at $commit"
    working="$commit"
    cp target/*.war build_cache/ 2>/dev/null || echo "⚠️ No .war file to save"
    break
  else
    echo "❌ Build failed"
  fi
done

if [ -z "$working" ]; then
  echo "❗ No working commit found"
  exit 1
fi

found_working=""
for commit in "${commits[@]}"; do
  if [[ "$commit" == "$working" ]]; then
    found_working=1
    continue
  fi

  if [[ -z "$found_working" ]]; then
    continue
  fi

  echo "⏳ Checking commit for regression: $commit"
  prepare_checkout
  git checkout "$commit" --quiet

  mvn clean package -q
  if [ $? -ne 0 ]; then
    echo "🚨 Build failed at $commit - regression found"
    regression="$commit"
    break
  else
    echo "✅ Build passed"
  fi
done

if [ -z "$regression" ]; then
  echo "ℹ️ No regression commit found after $working. No diff needed."
  prepare_checkout
  git checkout main --quiet
else
  echo "📝 Creating diff between $working and $regression"
  git diff "$working" "$regression" > regression_diff.txt
  echo "📄 Diff saved to regression_diff.txt"
fi

prepare_checkout
git checkout main --quiet
echo "♻️ Restoring build artifact..."
mkdir -p target
cp build_cache/*.war target/ 2>/dev/null || echo "⚠️ No cached .war to restore"

echo "✅ Git history analysis completed."

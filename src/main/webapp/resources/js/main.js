function updateHiddenX(value, checkbox) {
    const xInputHidden = document.getElementById("form:x");

    if (checkbox.checked) {
        xInputHidden.value = value;
    }
}

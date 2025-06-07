const scale = 40;
let centerX, centerY, ctx = [];

function drawCoordinatePlane(canvas) {
    ctx = canvas.getContext('2d');
    centerX = canvas.width / 2;
    centerY = canvas.height / 2;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(canvas.width, centerY);
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, canvas.height);
    ctx.strokeStyle = 'black';
    ctx.lineWidth = 2;
    ctx.stroke();

    ctx.font = '9px Arial';
    ctx.fillStyle = 'black';
    for (let i = -4; i <= 4; i++) {
        if (i !== 0) {
            ctx.fillText(i, centerX + i * scale - 5, centerY + 15);
            ctx.beginPath();
            ctx.moveTo(centerX + i * scale, centerY - 5);
            ctx.lineTo(centerX + i * scale, centerY + 5);
            ctx.stroke();

            ctx.fillText(-i, centerX - 15, centerY + i * scale + 5);
            ctx.beginPath();
            ctx.moveTo(centerX - 5, centerY + i * scale);
            ctx.lineTo(centerX + 5, centerY + i * scale);
            ctx.stroke();
        }
    }
}

function drawPlot(canvas, r) {
    ctx = canvas.getContext('2d');
    const scaledR = r * scale;

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawCoordinatePlane(canvas);

    ctx.fillStyle = 'rgba(0, 100, 255, 0.5)';
    ctx.strokeStyle = 'black';

    ctx.beginPath();
    ctx.arc(centerX, centerY, scaledR, Math.PI, Math.PI * 3 / 2);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    ctx.beginPath();
    ctx.rect(centerX, centerY - scaledR, scaledR / 2, scaledR);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(centerX - scaledR / 2, centerY);
    ctx.lineTo(centerX, centerY);
    ctx.lineTo(centerX, centerY + scaledR / 2);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();
}

function addPoint(canvas, x, y, isIn) {
    ctx = canvas.getContext('2d');

    const xCanvas = centerX + x * scale;
    const yCanvas = centerY - y * scale;

    ctx.beginPath();
    ctx.arc(xCanvas, yCanvas, 4, 0, Math.PI * 2);
    ctx.fillStyle = isIn ? 'blue' : 'red';
    ctx.fill();
    ctx.stroke();
}

function addPoints(canvas) {
    const rows = document.querySelectorAll('.resultTable tbody tr');

    rows.forEach(row => {
        const cells = row.querySelectorAll('td');

        const x = parseFloat(cells[0].textContent.trim());
        const y = parseFloat(cells[1].textContent.trim());
        const isIn = cells[3].textContent.trim() === 'Да';

        addPoint(canvas, x, y, isIn);
    });
}

function handleCanvasClick(event, canvas, xInputHidden, yInput, rSelect, submitBtn) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = event.clientX - rect.left;
    const mouseY = event.clientY - rect.top;

    const x = (mouseX - centerX) / scale;
    const y = (centerY - mouseY) / scale;

    xInputHidden.value = x.toFixed(2);
    yInput.value = y.toFixed(2);

    submitBtn.click();
}

function onAjax(data) {
    if (data.status === "success") {
        redrawPlot();
    }
}

function redrawPlot() {
    const canvas = document.getElementById("plot-canvas")
    const rSelect = document.getElementById("form:r")
    const r = rSelect.value

    drawPlot(canvas, r)
    addPoints(canvas)
}


window.onload = function () {
    const canvas = document.getElementById("plot-canvas");
    const xInputHidden = document.getElementById("form:x");
    const yInput = document.getElementById("form:y");
    const rSelect = document.getElementById("form:j_idt18");
    const submitBtn = document.getElementById("form:submit");

    if (canvas) {
        ctx = canvas.getContext('2d');
        drawPlot(canvas, 3);
        addPoints(canvas);

        canvas.addEventListener("click", function (event) {
            handleCanvasClick(event, canvas, xInputHidden, yInput, rSelect, submitBtn);
        });
    } else {
        console.error("Canvas not found!");
    }
};

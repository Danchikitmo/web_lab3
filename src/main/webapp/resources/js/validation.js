function showError(message) {
    const errorContainer = document.getElementById("error-message2");
    errorContainer.style.display = "block";
    errorContainer.innerHTML = message;

    setTimeout(hideError, 5000);
}

function hideError() {
    const errorContainer = document.getElementById("error-message2");
    errorContainer.style.display = "none";
}

function validateForm() {
    let isValid = true;
    let errorMessage = "";

    let xSelected = 0;
    document.querySelectorAll(".checkbox-x").forEach(function(checkbox) {
        if (checkbox.checked) {
            xSelected++;
        }
    });
    if (xSelected !== 1) {
        errorMessage += "Выберите одно значение для X!<br/>";
        isValid = false;
    }

    let yInput = document.querySelector(".inputY");
    let yValue = parseFloat(yInput.value);
    if (isNaN(yValue) || yValue < -3 || yValue > 3) {
        errorMessage += "Значение Y должно быть числом от -3 до 3<br/>";
        isValid = false;
    }

    if (isValid) {
        hideError();
    } else {
        showError(errorMessage);
    }

    return isValid;
}

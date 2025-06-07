function updateTime() {
    const clock = document.querySelector(".clock");
    const time = new Date();
    if (clock) {
        clock.innerHTML = time.toLocaleTimeString();
    } else {
        console.error("CLOCK NOT FOUND!!!");
    }
}

setInterval(updateTime, 6000);
window.onload = updateTime;
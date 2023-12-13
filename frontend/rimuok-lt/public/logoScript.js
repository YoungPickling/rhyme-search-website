const list = document.querySelectorAll("#R, #I, #M, #U, #O, #K, #dot, #L, #T");
let delayMS = 0;

function addWiggle() {
  this.classList.add("wiggle");
  this.addEventListener("animationend", () => {
    this.classList.remove("wiggle");
  })
}

function removeWiggle() {
  this.addEventListener("animationend", () => {
    this.classList.remove("wiggle");
  })
}

list.forEach(x => {
  delayMS += 15;
  x.style.animationDelay = delayMS + "ms"
  x.classList.add("logo_init");
  x.addEventListener("animationend", () => {
    x.style.transform = "scale(1)";
    x.classList.remove("logo_init");
    x.addEventListener("mouseenter", addWiggle);
    x.addEventListener("mouseleave", removeWiggle);
  });
})
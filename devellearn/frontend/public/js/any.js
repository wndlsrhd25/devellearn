
const elts = {
    anytext1: document.getElementById("anytext1"),
    anytext2: document.getElementById("anytext2")
};

const texts = [
    "If",
    "You",
    "Like",
    "It",
    "Please",
    "Give",
    "a Love",
    ":)",
    "by @DotOnion"
];

const morphTime = 1;
const cooldownTime = 0.25;

let textIndex = texts.length - 1;
let time = new Date();
let morph = 0;
let cooldown = cooldownTime;

elts.anytext1.textContent = texts[textIndex % texts.length];
elts.anytext2.textContent = texts[(textIndex + 1) % texts.length];

function doMorph() {
    morph -= cooldown;
    cooldown = 0;

    let fraction = morph / morphTime;

    if (fraction > 1) {
        cooldown = cooldownTime;
        fraction = 1;
    }

    setMorph(fraction);
}

function setMorph(fraction) {
    elts.anytext2.style.filter = `blur(${Math.min(8 / fraction - 8, 100)}px)`;
    elts.anytext2.style.opacity = `${Math.pow(fraction, 0.4) * 100}%`;

    fraction = 1 - fraction;
    elts.anytext1.style.filter = `blur(${Math.min(8 / fraction - 8, 100)}px)`;
    elts.anytext1.style.opacity = `${Math.pow(fraction, 0.4) * 100}%`;

    elts.anytext1.textContent = texts[textIndex % texts.length];
    elts.anytext2.textContent = texts[(textIndex + 1) % texts.length];
}

function doCooldown() {
    morph = 0;

    elts.anytext2.style.filter = "";
    elts.anytext2.style.opacity = "100%";

    elts.anytext1.style.filter = "";
    elts.anytext1.style.opacity = "0%";
}

function animate() {
    requestAnimationFrame(animate);

    let newTime = new Date();
    let shouldIncrementIndex = cooldown > 0;
    let dt = (newTime - time) / 1000;
    time = newTime;

    cooldown -= dt;

    if (cooldown <= 0) {
        if (shouldIncrementIndex) {
            textIndex++;
        }

        doMorph();
    } else {
        doCooldown();
    }
}

animate();

function updatePasswordStrength(password) {
    let strength = calculatePasswordStrength(password);
    let progressBar = document.getElementById("PwdMeter");
    let signupButton = document.getElementById("signupButton");
    if (strength === 1) {
      progressBar.style.width = "25%";
      progressBar.classList.add("bg-danger");
      signupButton.disabled = true;
    } else if (strength === 2) {
      progressBar.style.width = "50%";
      progressBar.classList.remove("bg-danger");
      progressBar.classList.add("bg-warning");
      signupButton.disabled = true;
    } else if (strength === 3) {
      progressBar.style.width = "75%";
      progressBar.classList.add("bg-warning");
      signupButton.disabled = false;
    } else if (strength === 4 || strength === 5) {
      progressBar.style.width = "100%";
      progressBar.classList.remove("bg-warning");
      progressBar.classList.remove("bg-danger");
      progressBar.classList.add("bg-success");
    } else {
      progressBar.style.width = "0%";
      signupButton.disabled = true;
    }
  }

  function calculatePasswordStrength(password) {
    let strength = 0;
    if (password.length >= 8) {
      strength += 1;
    }
    if (password.match(/[a-z]/)) {
      strength += 1;
    }
    if (password.match(/[A-Z]/)) {
      strength += 1;
    }
    if (password.match(/[0-9]/)) {
      strength += 1;
    }
    if (password.match(/[!@#$%^&*()\-_=+{};:,<.>]/)) {
      strength += 1;
    }
    return strength;
  }
  
  // Example usage
  const passwordInput = document.getElementById("passwordInput");
  
  passwordInput.addEventListener("input", function () {
    const password = passwordInput.value;
    updatePasswordStrength(password);
  });
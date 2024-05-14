window.onload = function () {
  var yourElement = document.getElementById("imgbacktemp");
  function setHeight() {
    var height = document.documentElement.scrollHeight + "px";
    yourElement.style.height = height;
  }
  setHeight();
  window.addEventListener("resize", setHeight);
};


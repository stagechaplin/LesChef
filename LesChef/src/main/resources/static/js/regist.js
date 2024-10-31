const add_textbox = () => {
  const box = document.getElementById("box");
  const newP = document.createElement('p');
  newP.innerHTML = `<input type='text'> <input type='text'> <input type='button' value='-' onclick='remove(this)'>`;
  box.appendChild(newP);
}
const remove = (obj) => {
  document.getElementById('box').removeChild(obj.parentNode);
}
document.addEventListener('DOMContentLoaded', function() {
  const fileInput = document.getElementById('imageInput');
  let currentImgElement = null;

  // body에 클릭 이벤트 리스너 추가
  document.body.addEventListener('click', function(e) {
      if (e.target.classList.contains('clickable-img')) {
          currentImgElement = e.target;
          fileInput.click();
      }
  });

  // 파일 입력에 변경 이벤트 리스너 추가
  fileInput.addEventListener('change', function(event) {
      const file = event.target.files[0];
      if (file && currentImgElement) {
          const reader = new FileReader();
          reader.onload = function(e) {
              currentImgElement.src = e.target.result;
          };
          reader.readAsDataURL(file);
      }
  });
});

let counter = 1;

const add_textbox1 = () => {
  const newId = 'toggleImage' + counter++;
  const box = document.getElementById("box1");
  const newP = document.createElement('p');
  newP.innerHTML = `
      <img src='/img/profile.png' alt='Click to change image' id='${newId}' class='clickable-img'>
      <textarea></textarea> 
      <input type='button' value='-' onclick='remove1(this)'>
  `;
  box.appendChild(newP);
};

const remove1 = (obj) => {
  document.getElementById('box1').removeChild(obj.parentNode);
};
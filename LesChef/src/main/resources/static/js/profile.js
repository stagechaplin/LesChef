document.addEventListener('DOMContentLoaded', function() {
  const imgElement = document.getElementById('toggleImage');
  const fileInput = document.getElementById('imageInput');
  

  imgElement.addEventListener('click', function() {
    fileInput.click();
  });

  fileInput.addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        imgElement.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });
});

document.addEventListener('DOMContentLoaded', function() {
  const imgElement = document.getElementById('toggleImage1');
  const fileInput = document.getElementById('imageInput');
  

  imgElement.addEventListener('click', function() {
    fileInput.click();
  });

  fileInput.addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        imgElement.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });
});

// document.addEventListener('DOMContentLoaded', function() {
//   const imgElement = document.getElementById('toggleImage2');
//   const fileInput = document.getElementById('imageInput');
  

//   imgElement.addEventListener('click', function() {
//     fileInput.click();
//   });

//   fileInput.addEventListener('change', function(event) {
//     const file = event.target.files[0];
//     if (file) {
//       const reader = new FileReader();
//       reader.onload = function(e) {
//         imgElement.src = e.target.result;
//       };
//       reader.readAsDataURL(file);
//     }
//   });
// });

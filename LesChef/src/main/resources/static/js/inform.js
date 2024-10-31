// 평점: 별 선택시 갯수 채우는 함수

const stars = document.querySelectorAll('.countingstar');

stars.forEach(star => {
  star.addEventListener('click', function(){
    const value = parseInt(star.getAttribute('star'));
    
    // 별 초기화 
    stars.forEach(function(s, index) {
      s.setAttribute('src',"../img/레시피 아이콘/사용자빈별.png");
      if (index < value) {
        s.setAttribute("src","../img/레시피 아이콘/사용자별.png");
      }
    });
  });
});

// 댓글 작성 버튼 클릭 시 조회에 뿌리기

const send = document.querySelector('#resist');
const coment = document.querySelector('#areasize');
const container = document.querySelector('#comentcontainer');

const firsttext = document.querySelector('.comentbox:nth-of-type(1)');

  send.addEventListener('click',()=>{
    const cloneBox = firsttext.cloneNode(true);
    const cloneStars = cloneBox.querySelectorAll('.comentstar');

    let count = 0;

    cloneBox.querySelector('.comenttext').textContent = coment.value;

    stars.forEach(function(star){
      if(star.getAttribute('src') == '../img/레시피 아이콘/사용자별.png'){
        count += 1;
      }
    });

    cloneStars.forEach(function(cloneStar, index){
      if(index < count){
        cloneStar.setAttribute('src', "../img/레시피 아이콘/사용자별.png");
      }else{
        cloneStar.setAttribute("src", "../img/레시피 아이콘/사용자빈별.png");
      }
    });


    container.append(cloneBox);
    
    coment.value = "";
  });


// 해당 페이지 좋아요 클릭시 하트 변경

  const heart = document.querySelector('#asideicon2');
  let isHeartFilled = false; // 하트가 채워진 상태 여부를 기억할 변수
  
  heart.addEventListener('click', () => {
      if (!isHeartFilled) {
          heart.src = '../img/레시피 아이콘/하트100.png';
          isHeartFilled = true;
      } else {
          heart.src = '../img/레시피 아이콘/하트빈100.png';
          isHeartFilled = false;
      }
  });
//해시 태그
const commentNames = document.querySelectorAll('.commentWriterName');
const closeTag = document.querySelector('#closeTag');
const hashTag = document.getElementById('hashTag');
let tagName;

commentNames.forEach((name) => {
  name.addEventListener("click", () => {
    

    if(hashTag.style.display == "flex"){
      
      if(name.textContent == hashTag.querySelector('#nameTag').textContent.slice(1)){
        hashTag.style.display = "none";
      }
      else{
        console.log('확인');
        hashTag.querySelector('#nameTag').textContent = '@' + name.textContent;
        tagName = hashTag.querySelector('#nameTag').textContent;
      }
    }
    else{
      hashTag.querySelector('#nameTag').textContent = '@' +  name.textContent;
      tagName = hashTag.querySelector('#nameTag').textContent;
      hashTag.style.display = "flex";
    }
  })
})


closeTag.addEventListener("click", () => {
  console.log("aaaa");
  if(hashTag.style.display == "flex"){
    hashTag.style.display = "none";
  }
})






/* 댓글 추가 */
function addComment(event){
  event.preventDefault();
  const commentInput = event.target.querySelector(".commentInput");
  var commentText = commentInput.value;
  var date = new Date().toLocaleString();

  var commentDiv = document.createElement("div");
  commentDiv.className = "card my-3";

  if(hashTag.style.display == "flex"){

    commentDiv.innerHTML = 
      `<div class="comment">
        <img src="../img/img/fubao.jpg" class="commentWriterImg">
          <div class="commentContent">
          <p>
            <span class="commentWriterName">푸바오</span>
            <span class="commentDate">${date}</span>
          </p>
          <p class="commentText">${tagName} ${commentText}</p>
      </div>`;

    hashTag.style.display = "none";
  }else{
    commentDiv.innerHTML = 
      `<div class="comment">
        <img src="../img/img/fubao.jpg" class="commentWriterImg">
          <div class="commentContent">
          <p>
            <span class="commentWriterName">푸바오</span>
            <span class="commentDate">${date}</span>
          </p>
          <p class="commentText">${commentText}</p>
      </div>`;
  }  
  
  var commentsDiv = event.target.parentNode.querySelector(".commentRepeat");
  commentsDiv.insertBefore(commentDiv, commentsDiv.firstChild);

  commentInput.value= "";
}
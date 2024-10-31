document.querySelector('.delete-button').addEventListener('click', function() {
  document.querySelectorAll('.delete-checkbox:checked').forEach(checkbox => {
    checkbox.parentElement.remove();
  })
})
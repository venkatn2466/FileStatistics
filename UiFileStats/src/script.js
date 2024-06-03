const keyDisplayNames = {

    "wordCount": "Word Count",
   
    "letterCount": "Letter Count",
   
    "symbolCount": "Symbol Count",
   
    "topWords": "Top Words",
   
    "topLetters": "Top Letters"
   
   };
   
   
   
   
   document.getElementById('uploadForm').addEventListener('submit', function(e) {
   
    e.preventDefault();
   
    var fileInput = document.getElementById('fileInput');
   
    var file = fileInput.files[0];
   
    
   
    if (file) {
        var formData = new FormData();
       formData.append('file', file);
   
        fetch('http://localhost:8080/api/v1/file/upload', {
   
            method: 'POST',
   
            body: formData,
   
        })
   
       .then(response => {
    if (!response.ok) {
        return response.json().then(errorData => {
            throw new Error(errorData.errorMsg || 'Unknown error occurred.');
        });
    }
    return response.json();
  })
   
        .then(data => {
   
          var keyValuePairs = "";
   
          for (const key in data) {
   
            const displayName = keyDisplayNames[key] || key;
   
            if (Array.isArray(data[key])) {
   
                keyValuePairs += `<p><span class="key">${displayName}:</span><br>`;
   
                data[key].forEach(value => {
   
                    keyValuePairs += `<span class="value">${value}</span><br>`;
   
                });
   
                keyValuePairs += `</p>`;
   
            } else {
   
                keyValuePairs += `<p><span class="key">${displayName}:</span> <span class="value">${data[key]}</span></p>`;
   
            }
   
          }
   
          document.getElementById('status').innerHTML = keyValuePairs;
   
        })
   
        .catch(error => {
          const errorMessage = error || 'Please try after sometime.Service not Up'
          console.error('Error:', errorMessage);
   
          showErrorModal(errorMessage);
   
        });
   
    } else {
   
        alert('Please select a text file to upload.');
   
    }
   
   });
   
   
   
   
   function showErrorModal(message) {
    var modal = document.getElementById('errorModal');
    var errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message; // Update the error message
    var span = document.getElementsByClassName('close')[0]; 
    modal.style.display = 'block';
    span.onclick = function() {
      modal.style.display = 'none';
    }
    window.onclick = function(event) {
      if (event.target == modal) {
        modal.style.display = 'none';
      }
    }
    }
  
   
   
   
   
    document.getElementById('viewHistoryBtn').addEventListener('click', function() {

        var historyTable = document.getElementById('historyTable');
       
        var viewHistoryBtn = document.getElementById('viewHistoryBtn');
       
       
       
       
        if (historyTable.style.display === 'none') {
       
            fetch('http://localhost:8080/api/v1/file/upload/history')
       
                .then(response => response.json())
       
                .then(data => {
       
                    viewHistoryBtn.textContent = "Close History";
       
                    displayUploadHistory(data);
       
                })
       
                .catch(error => {
       
                    console.error('Error:', error);
       
                });
       
        } else {
       
            historyTable.style.display = 'none';
       
            viewHistoryBtn.textContent = "View History"; 
       
        }
       
       });
       
       
       
       
       // Function to display upload history in a table
       
       function displayUploadHistory(historyData) {
       
        var table = document.getElementById('historyTable');
       
        var tableHTML = '<tr><th>File Name</th><th></th><th>Upload Timestamp</th></tr>';
       
       
       
       
        historyData.forEach(entry => {
       
            tableHTML += `<tr><td>${entry.fileName}</td><td></td><td>${entry.uploadTimestamp}</td></tr>`;
       
        });
       
       
       
       
        table.innerHTML = tableHTML;
       
        table.style.display = 'block';
       
       }
   
   
   
   
  
   
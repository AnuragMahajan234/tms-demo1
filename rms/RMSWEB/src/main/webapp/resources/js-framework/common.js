$(this).oneTime(1*1000*60, function() {
	alert('1');
            setSessionTimeoutWarningMessage("Your session will be over in next 5 minutes, please submit the page to avoid data loss.");
      });
      
      $(this).oneTime(2*1000*60, function() {
            document.location.replace("/rms/logout");
      });

      
      function setSessionTimeoutWarningMessage(message) {
          $("#errorMsg").append("<li><font color='red'>" + message + "</font></li>"); 
    }

// Theme Switch 
$('.hint__pulse').show();
if (typeof (localStorage) == 'undefined') {
    alert('Your browser does not support HTML5 localStorage. Try upgrading.');
} else {
    if (localStorage.getItem("theme") != null) {
        getColour = localStorage.theme;
        $('html').addClass(getColour);
    } else {
        changeTheme('green_theme');
    }

    if (localStorage.getItem('isThemeChecked') === true) {
        $('.hint__pulse').hide();
    }
}

function changeTheme(type) {
    if (type) {
        // $('html').removeClass();
        $('html').attr("class", type);
        localStorage.setItem("theme", type);
    } else {
        localStorage.setItem("theme", 'green_theme');
        return;
    }
}

function openThemeDD() {
    localStorage.setItem('isThemeChecked', true);
    $('.hint__pulse').hide();    
}
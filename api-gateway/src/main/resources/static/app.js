$(document).ready(function(){
    // Initialize Tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Add smooth scrolling to all links in navbar + footer link
    $(".navbar a, footer a[href='#home']").on('click', function(event) {

        // Make sure this.hash has a value before overriding default behavior
        if (this.hash !== "") {

            // Prevent default anchor click behavior
            event.preventDefault();

            // Store hash
            var hash = this.hash;

            // Using jQuery's animate() method to add smooth page scroll
            // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
            $('html, body').animate({
                scrollTop: $(hash).offset().top
            }, 900, function(){

                // Add hash (#) to URL when done scrolling (default click behavior)
                window.location.hash = hash;
            });
        } // End if
    });

    var	headerTopHeight = $(".header-top").outerHeight(),
        headerHeight = $("header.header.fixed").outerHeight();
    $(window).scroll(function() {
        if (($(".header.fixed").length > 0)) {
            if(($(this).scrollTop() > headerTopHeight+headerHeight) && ($(window).width() > 767)) {
                $("body").addClass("fixed-header-on");
                $(".header.fixed").addClass('animated object-visible fadeInDown');
                if ($(".banner:not(.header-top)").length>0) {
                    $(".banner").css("marginTop", (headerHeight)+"px");
                } else if ($(".page-intro").length>0) {
                    $(".page-intro").css("marginTop", (headerHeight)+"px");
                } else if ($(".page-top").length>0) {
                    $(".page-top").css("marginTop", (headerHeight)+"px");
                } else {
                    $("section.main-container").css("marginTop", (headerHeight)+"px");
                }
            } else {
                $("body").removeClass("fixed-header-on");
                $("section.main-container").css("marginTop", (0)+"px");
                $(".banner").css("marginTop", (0)+"px");
                $(".page-intro").css("marginTop", (0)+"px");
                $(".page-top").css("marginTop", (0)+"px");
                $(".header.fixed").removeClass('animated object-visible fadeInDown');
            }
        };
    });

//Scroll totop
//-----------------------------------------------
    $(window).scroll(function() {
        if($(this).scrollTop() != 0) {
            $(".scrollToTop").fadeIn();
        } else {
            $(".scrollToTop").fadeOut();
        }
    });

    $(".scrollToTop").click(function() {
        $("body,html").animate({scrollTop:0},800);
    });
})



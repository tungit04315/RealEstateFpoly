(function($) {
    "use strict";

    // Spinner
    var spinner = function() {
        setTimeout(function() {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();


    // Initiate the wowjs
    new WOW().init();


    // Sticky Navbar
    $(window).scroll(function() {
        if ($(this).scrollTop() > 45) {
            $('.nav-bar').addClass('sticky-top');
        } else {
            $('.nav-bar').removeClass('sticky-top');
        }
    });


    // Back to top button
    $(window).scroll(function() {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function() {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Header carousel
    $(".header-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        items: 1,
        dots: true,
        loop: true,
        nav: true,
        navText: [
            '<i class="bi bi-chevron-left"></i>',
            '<i class="bi bi-chevron-right"></i>'
        ]
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        margin: 24,
        dots: false,
        loop: true,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsive: {
            0: {
                items: 1
            },
            992: {
                items: 2
            }
        }
    });

    $(function() {
        $('.custom-dropdown').on('show.bs.dropdown', function() {
            // $(this).find('.dropdown-menu').first().stop(false, false).slideDown();
            // $(this).find('.dropdown-menu').addClass('active');
            var that = $(this);
            setTimeout(function() {
                that.find('.dropdown-menu').addClass('active');
            }, 100);


        });
        $('.custom-dropdown').on('hide.bs.dropdown', function() {
            $(this).find('.dropdown-menu').removeClass('active');
        });
    });

    $(function() {
        $('#contact').click(function() {
            $('#contactForm').fadeToggle();
        })
        $(document).mouseup(function(e) {
            var container = $("#contactForm");

            if (!container.is(e.target) &&
                container.has(e.target).length === 0) {
                container.fadeOut();
            }
        });
    });

    $(function() {
        $('#contact-two').click(function() {
            $('#contactForm-two').fadeToggle();
        })
        $(document).mouseup(function(e) {
            var container = $("#contactForm-two");

            if (!container.is(e.target) &&
                container.has(e.target).length === 0) {
                container.fadeOut();
            }
        });
    });

    $(function() {
        $('#editPost').click(function() {
            $('#editPostForm').fadeToggle();
        })
        $(document).mouseup(function(e) {
            var container = $("#editPostForm");

            if (!container.is(e.target) &&
                container.has(e.target).length === 0) {
                container.fadeOut();
            }
        });
    });
})(jQuery);
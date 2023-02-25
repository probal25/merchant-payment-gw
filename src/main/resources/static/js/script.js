$(document).ready(function () {
    $('#my-form').submit(function (event) {
        event.preventDefault();
        if (validateForm()) {
            $(this).unbind('submit').submit();
        }
    });
});

function validateForm() {
    var valid = true;

    if ($('#wallet').val().trim() == '') {
        $('#wallet').addClass('is-invalid');
        $('#wallet-error').text('Wallet Number is required');
        valid = false;
    } else {
        $('#wallet').removeClass('is-invalid');
        $('#wallet-error').text('');
    }

    if ($('#pin').val().trim() == '') {
        $('#pin').addClass('is-invalid');
        $('#pin-error').text('Email is required');
        valid = false;
    } else {
        $('#pin').removeClass('is-invalid');
        $('#pin-error').text('');
    }

    return valid;
}



$(document).ready(function () {
    searchDocente();
    searchMateria();
});

function searchDocente() {
    console.log("Estoy en searchDocente");
    $.post('../ControladorGrupos?action=optionDocente', {}, function (response) {
        $('#optionDocente').html(response);
    });
}
function searchMateria() {
    console.log("Estoy en searchMateria");
    $.post('../ControladorGrupos?action=optionMateria', {}, function (response) {
        $('#optionMateria').html(response);
    });
}


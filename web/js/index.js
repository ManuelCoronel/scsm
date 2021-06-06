/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function (){
    search();
});

function search(){
    $.post('../ControladorPrograma?accion=listar', {}, function(response){
        $('#pro_option').html(response);
    });
}


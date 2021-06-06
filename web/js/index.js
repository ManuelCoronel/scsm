/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function (){
    search();
    searchFacultad();
    searchDepartamento();
});

function search(){
    $.post('../ControladorPrograma?accion=listar', {}, function(response){
        $('#pro_option').html(response);
    });
    
}

function searchFacultad(){
    console.log("buscando Facultad");
    $.post('../ControladorFacultad?accion=listar', {}, function(response){
        $('#optionFacultad').html(response);
    });
}

function searchDepartamento(){
    $.post('../ControladorDepartamento?accion=listar', {}, function(response){
        $('#optionDepartamento').html(response);
    });
}
$(function() {
	$( ".accordion" ).accordion();
	$( ".tabs" ).tabs();
	$(".button").button();
	$("#available-sequences").resizable({containment: "#body"});
	$("#align-from").resizable({containment: "#body"});
	$("#align-to").resizable({containment: "#body"});
	$( "#align-from-list, #align-to-list, #available-sequences" ).sortable({
	      connectWith: ".dropList"
	}).disableSelection();
	//$('.nodrag').draggable( "disable" )
	$("#droppable").droppable({
	      drop: function( event, ui ) {
		$( this )
		  .addClass( "ui-state-highlight" )
		  .find( "p" )
		    .html( "Dropped!" );
	      }
	    });
	$(".uibtn").button();
	$( "#proteinId" ).autocomplete({
		source: function( request, response ) {
		  $.getJSON( "get_autocomplete_ids.cgi", {
		    limit:10,
		     prefix: request.term,
		     db: $("#dbselect").val()
		  }, response);
		},
	      select: function( event, ui ) {
		   //"Selected: " + ui.item.value + " aka " + ui.item.id :
		  //"Nothing selected, input was " + this.value );
	    }
	});
});
function showAddMatrixDialog() {
    $("#addMatrixDialog").dialog({autoOpen: false,modal: true,bgiframe: true,width:500,height:250});
    $('#addMatrixDialog').dialog('open');
}
function hideAddMatrixDialog() {
    if ($('#addMatrixDialog').dialog('isOpen')) {
	$('#addMatrixDialog').dialog('close');
    }
}

$(".accordion").accordion();
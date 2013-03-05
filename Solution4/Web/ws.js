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
	 $(".sequence-drop").droppable({
	    accept: ".sequence",
	    activeClass: "ui-state-highlight",
	    drop: function( event, ui ) {
	      deleteImage( ui.draggable );
	    }
	});
	$(".uibtn").button();
	$( "#alignmentMatrix" ).autocomplete({
		source: function( request, response ) {
		  $.getJSON( "matrix/name_autocomplete.cgi", {
		    limit:10,
		    prefix: request.term
		  }, response);
		},
	      select: function( event, ui ) {
		   //"Selected: " + ui.item.value + " aka " + ui.item.id :
		  //"Nothing selected, input was " + this.value );
	    }
	});
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
	$(".accordion").accordion();
	renderSequences();
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
function showAlignment() {
  
}
function showFixedPointAlignment() {
  
}
/**
 * Add sequences from local storage
 */
function renderSequences() {
  if($("#availableSequencesList").size() > 0) {
    $("#availableSequencesList").empty();
    var sequences = $.jStorage.get("sequences", []);
    for(var i=0; i<sequences.length; i++ ) {
      $("#availableSequencesList").append("<li class=\"sequence ui-state-default\" seqid=\"abc\">Sequence " + sequences[i] + "</li>");
    }
  } else {
    console.log("Can't find available sequences list while trying to add sequence");
  }
}

function addSequence(id) {
  var sequences = $.jStorage.get("sequences", []);
  sequences.push(id);
  renderSequences();
}

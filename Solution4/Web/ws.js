$(function() {
	$( ".accordion" ).accordion();
	$( ".tabs" ).tabs();
	$(".button").button();
	$("#available-sequences").resizable({containment: "#body"});
	$("#align-from").resizable({containment: "#body"});
	$("#align-to").resizable({containment: "#body"});
	//$('.nodrag').draggable( "disable" )
	 $(".sequence").draggable({
	    revert: "invalid", // when not dropped, the item will revert back to its initial position
	    containment: "document",
	 });
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
	$( "#dbid" ).autocomplete({
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
      var seqObj = sequences[i];
      $("#availableSequencesList").append("<li class=\"sequence ui-state-default ui-widget-content ui-corner-tr\" seqid=\"seqObj.id\">Sequence " + seqObj.name + "(" + seqObj.type + ")" + "</li>");
    }
    //Calculate the height of the container
    var height = sequences.length*35+40;
    alert(height);
    $("#availableSequencesList").css("height",height+"px");
  } else {
    console.log("Can't find available sequences list while trying to add sequence");
  }
}

function addSequenceFromDB() {
  var db = $("#dbselect").find("option:selected").text();
  var id = $("#dbid").val();
  var type = "";
  if(db == "PDB") {
    type = "Protein";
  } else if(db == "UniProt") {
    type = "Protein";
  } else if(db == "RefSeq") {
    type = "Nucleotide";
  } else {
    alert("Can't find sequence type by database " + db);
  }
  //Add id (e.g. pdb:1ULI)
  addSequence(toLowerCase(db) + ":" id, db + ":" + id, type)
}

function addSequence(id, name, type) {
  var sequences = $.jStorage.get("sequences", []);
  sequences.push({id: id, name: name, type: type});
  var sequences = $.jStorage.set("sequences", sequences);
  renderSequences();
}

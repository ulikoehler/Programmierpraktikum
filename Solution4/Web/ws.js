$(function() {
	$( ".accordion" ).accordion();
	$( ".tabs" ).tabs();
	$(".button").button();
	$("#available-sequences").resizable({containment: "#body"});
	$("#align-from").resizable({containment: "#body"});
	$("#align-to").resizable({containment: "#body"});
	//$('.nodrag').draggable( "disable" )
	$("#alignmentSequence1").droppable({
	    accept: ".sequence",
	    activeClass: "ui-state-highlight",
	    drop: function( event, ui ) {
	      setAlignmentSequence1( ui.draggable );
	    }
	});
	$("#alignmentSequence2").droppable({
	    accept: ".sequence",
	    activeClass: "ui-state-highlight",
	    drop: function( event, ui ) {
	      setAlignmentSequence2( ui.draggable );
	    }
	});
	$("#sspSequenceDropField").droppable({
	    accept: ".sequence",
	    activeClass: "ui-state-highlight",
	    drop: function( event, ui ) {
	      setSSPSequence( ui.draggable );
	    }
	});
	$("#deleteSequenceDrop").droppable({
	    accept: ".sequence",
	    activeClass: "ui-state-highlight",
	    drop: function( event, ui ) {
	      deleteSequence( ui.draggable );
	    }
	});
	$("#showSequenceDrop").droppable({
	    accept: ".sequence",
	    activeClass: "ui-state-highlight",
	    drop: function( event, ui ) {
	      showSequence( ui.draggable );
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
	$( "#gorModelInput" ).autocomplete({
		source: function( request, response ) {
		  $.getJSON( "gormodels/name_autocomplete.cgi", {
		    limit:10,
		     prefix: request.term,
		  }, response);
		},
	      select: function( event, ui ) {
		    //"Selected: " + ui.item.value + " aka " + ui.item.id :
		  //"Nothing selected, input was " + this.value );
	    }
	});
	$( "#modelInput" ).autocomplete({
		source: function( request, response ) {
		  $.getJSON( "gormodels/name_autocomplete.cgi", {
		    limit:10,
		     prefix: request.term,
		  }, response);
		},
	      select: function( event, ui ) {
		    //"Selected: " + ui.item.value + " aka " + ui.item.id :
		  //"Nothing selected, input was " + this.value );
	    }
	});
	$(".accordion").accordion();
	renderSequences();
	//Disable gap open when NW is selected
	$("#nwRadio").change(function() {
	  $("#gapOpenField").prop("disabled", $("#nwRadio").attr("checked") == false);
	});
});
function setAlignmentSequence1(elem) {
  $("#alignmentSeq1Id").val($(elem).attr("seqid"));
  $(elem).addClass("ui-state-highlight");
  //$(elem).draggable("disable");
}
function setAlignmentSequence2(elem) {
  $("#alignmentSeq2Id").val($(elem).attr("seqid"));
  $(elem).addClass("ui-state-highlight");
  //$(elem).draggable("disable")
}
function deleteSequence(elem) {
  var idToDelete = $(elem).attr("seqid");
  //Remove the sequence from the array
  var sequences = $.jStorage.get("sequences", []);
  var newSequences = [];
  for(var i=0; i<sequences.length; i++ ) {
      var seqObj = sequences[i];
      if(seqObj.id != idToDelete) {
	  newSequences.push(seqObj);
      }
  }
  $.jStorage.set("sequences", newSequences);
  renderSequences();
}
function setSSPSequence(elem) {
  $("#sspSequenceField").val($(elem).attr("seqid"));
  $(elem).addClass("ui-state-highlight");
  //$(elem).draggable("disable")
}
function showSequence(elem) {
  $.get("sequences/get_sequence.cgi", {id: $(elem).attr("seqid")},
	function(data) {
	    $("#sequenceDialog").attr("title","Sequence of " + $(elem).attr("name"));
	    $("#sequenceDialog").dialog({autoOpen: false,modal: true,bgiframe: true,width:500,height:250});
	    $("#sequenceDialog").empty();
	    $("#sequenceDialog").append("<div>" + data + "</div>");
	    $("#sequenceDialog").dialog("open");
	});
  $(elem).draggable({revert: true});
  //$(elem).draggable("disable")
}
function refreshDragDrop() { 
      $(".sequence").draggable({
	revert: "invalid", // when not dropped, the item will revert back to its initial position
	containment: "document",
      });
}
function showAddMatrixDialog() {
    $("#addMatrixDialog").dialog({autoOpen: false,modal: true,bgiframe: true,width:500,height:250});
    $('#addMatrixDialog').dialog('open');
}
function hideAddMatrixDialog() {
    if ($('#addMatrixDialog').dialog('isOpen')) {
	$('#addMatrixDialog').dialog('close');
    }
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
      $("#availableSequencesList").append("<li class=\"sequence ui-state-default ui-widget-content ui-corner-tr\" name=\"" + seqObj.name + "\" seqid=\""+ seqObj.id + "\">Sequence " + seqObj.name + "(" + seqObj.type + ")" + "</li>");
    }
    //Calculate the height of the container
    var height = sequences.length*35+60;
    $("#available-sequences").css("height",height+"px");
  } else {
    console.log("Can't find available sequences list while trying to add sequence");
  }
  refreshDragDrop();
}

function showAlignment(fixedPoint) {
  //Get all the field values
  var alignmentSeq1Id = $("#alignmentSeq1Id").val();
  var alignmentSeq2Id = $("#alignmentSeq2Id").val();
  if(!alignmentSeq1Id || !alignmentSeq2Id) {
    alert("Please drag sequences into both sequence boxes");
    return;
  }
  //
  var distanceMatrix = $("#alignmentMatrix").val();
  if(!distanceMatrix) {
    alert("Please specify a distance matrix, e.g. BLOSUM45");
    return;
  }
  var alignmentType = $("input[name=alignmentType]:checked").val();
  var alignmentAlgorithm = $("input[name=alignmentAlgorithm]:checked").val();
  var gapOpenPenalty = $("input[name=gapOpenPenalty]").val();
  var gapExtensionPenalty = $("input[name=gapExtendPenalty]").val();
  var calculateSSAA = ($("#calculateSSAA").attr("checked") == true);
  //Show the progress bar & dialog
  $("#alignmentResultDialog").empty();
  $("#alignmentResultDialog").append("<div id=\"alignmentProgressBar\"></div>");
  $("#alignmentProgressBar").progressbar({
      value: false
    });
  $("#alignmentResultDialog").dialog({autoOpen: false,modal: false,bgiframe: true,width:1000,height:750});
  $('#alignmentResultDialog').dialog('open');
  //
  $.post("alignment/alignment.cgi", {
    alignmentSeq1Id: alignmentSeq1Id,
    alignmentSeq2Id: alignmentSeq2Id,
    distanceMatrix: distanceMatrix,
    alignmentType: alignmentType,
    alignmentAlgorithm: alignmentAlgorithm,
    gapOpenPenalty: gapOpenPenalty,
    gapExtendPenalty: gapExtensionPenalty,
    calculateSSAA: calculateSSAA,
    fixedPoint: fixedPoint
  }, function(data, textStatus) {
    //Replace the progress bar by the data
    $("#alignmentResultDialog").empty();
    $("#alignmentResultDialog").append(data);
  });
}


function validateAli(alignment, reference) {
  //Get all the field values
  var alignment = $("#valAliAlignmentsField").val();
  var reference = $("#valAliRefField").val();
  //Show the progress bar & dialog
  $("#validateDialog").empty();
  $("#validateDialog").append("<div id=\"alignmentProgressBar\"></div>");
  $("#validateDialog").progressbar({
      value: false
    });
  $("#validateDialog").dialog({autoOpen: false,modal: false,bgiframe: true,width:1000,height:750});
  $('#validateDialog').dialog('open');
  //
  $.get("validation/validateAli.cgi", {
    alignment: alignment,
    reference: reference
  }, function(data, textStatus) {
    //Replace the progress bar by the data
    $("#validateDialog").empty();
    $("#validateDialog").append(data);
  });
}


function showSSP(fixedPoint) {
  //Get all the field values
  var seqId = $("#sspSequenceField").val();
  var gor5Alignment = $("#gor5AlignmentField").val();
  var model = $("#modelInput").val();
  
  var probabilities = ($("input[name=probabilities]:checked").attr("checked") ? 1 : 0);
  var avgPost = ($("input[name=avgPost]:checked").attr("checked") ? 1 : 0);
  var stdPost = ($("input[name=stdPost]:checked").attr("checked") ? 1 : 0);
  
  var avgProb = $("#avgProb").val();
  var stdProb = $("#stdProb").val();
  //Show the progress bar & dialog
  $("#sspDialog").empty();
  $("#sspDialog").append("<div id=\"alignmentProgressBar\"></div>");
  $("#sspDialog").progressbar({
      value: false
    });
  $("#sspDialog").dialog({autoOpen: false,modal: false,bgiframe: true,width:1500,height:750});
  $("#sspDialog").dialog("open");
  //
  $.post("ssp/sspPredict.cgi", {
    model: model,
    gor5AlignmentField: gor5Alignment,
    sspSequence: seqId,
    probabilities: probabilities,
    avgPost : avgPost,
    stdpost: stdPost,
    avgValue: avgProb,
    stdValue: stdProb
  }, function(data, textStatus) {
    //Replace the progress bar by the data
    $("#sspDialog").empty();
    $("#sspDialog").append(data);
  });
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
  var key = db.toLowerCase() + ":" + id;
  var name = "[" + db + "] " + id;
  addSequence(key, name, type)
}

function addSequence(id, name, type) {
  var sequences = $.jStorage.get("sequences", []);
  sequences.push({id: id, name: name, type: type});
  var sequences = $.jStorage.set("sequences", sequences);
  renderSequences();
}

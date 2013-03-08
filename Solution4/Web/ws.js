$(function() {
	$( ".accordion" ).accordion();
	$( ".tabs" ).tabs();
	$(".button").button();
	$("#available-sequences").resizable({containment: "#available-sequences-container"});
	$("#available-sequences").css("width" , "1200px");
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
	//$(".accordion").accordion();
	renderSequences();
	//Disable gap open when NW is selected
	$("#nwRadio").change(function() {
	  if($("#nwRadio").attr("checked")) {
	    $("#gapOpenField").prop("disabled", "disabled");
	  } else { //Gotoh
	    $("#gapOpenField").removeAttr("disabled");
	  }
	});
});
function setAlignmentSequence1(elem) {
  $("#alignmentSeq1Id").val($(elem).attr("seqid"));
  $(elem).addClass("ui-state-highlight");
  $("#alignmentSeq1Name").text($(elem).attr("name"));
  //$(elem).draggable("disable");
}
function setAlignmentSequence2(elem) {
  $("#alignmentSeq2Id").val($(elem).attr("seqid"));
  $(elem).addClass("ui-state-highlight");
  $("#alignmentSeq2Name").text($(elem).attr("name"));
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
  function closure(elem){
    $.get("sequences/get_sequence.cgi", {id: $(elem).attr("seqid")},
	  function(data) {
	      var id = "seq-" + $(elem).attr("seqid");
	      id = id.replace(":","-");
	      $("#" + id).remove();
	      $("#dialogsContainer").append('<div id="' + id + '" style="display: none;"></div>')
	      var dialog = $("#" + id);
	      dialog.attr("title","Sequence of " + $(elem).attr("name"));
	      dialog.dialog({autoOpen: false,modal: false,bgiframe: true,width:500,height:250});
	      dialog.empty();
	      dialog.append("<div style=\"word-wrap:break-word;\">" + data + "</div>");
	      dialog.dialog("open");
	  }
    );
  }
  //Call the closure
  closure(elem);
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
  //Clear the fields
  $("#sspSequenceField").val("");
  $("#alignmentSeq1Id").val("");
  $("#alignmentSeq2Id").val("");
  $("#alignmentSeq1Name").val("");
  $("#alignmentSeq2Name").val("");

  
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

function showAlignment() {
    //Get all the field values
    var alignmentSeq1Id = $("#alignmentSeq1Id").val();
    var alignmentSeq2Id = $("#alignmentSeq2Id").val();
    var seq1Name = $("#alignmentSeq1Name").text();
    var seq2Name = $("#alignmentSeq2Name").text();
    //Initialize the dialog
    var id = "alig-" + alignmentSeq1Id + "-" + alignmentSeq2Id + "-" + new Date().getTime(); //Allow same alignment (e.g. with different params) multiple times
    id = id.replace(/:/g,"-");
    $("#" + id).remove();
    $("#dialogsContainer").append('<div id="' + id + '" style="display: none;"></div>')
    var dialog = $("#" + id);
    dialog.attr("title","Alignment of " + seq1Name + " and " + seq2Name);
    dialog.dialog({autoOpen: false,modal: false,bgiframe: true,width:1200,height:500});
    
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
    var gorModel = $("#gorModelInput").val();
    //Show the progress bar & dialog
    dialog.empty();
    dialog.append("<div id=\"alignmentProgressBar\"></div>");
    $("#alignmentProgressBar").progressbar({
	value: false
      });
    dialog.dialog("open");
    //Request
    $.post("alignment/alignment.cgi", {
      alignmentSeq1Id: alignmentSeq1Id,
      alignmentSeq2Id: alignmentSeq2Id,
      distanceMatrix: distanceMatrix,
      alignmentType: alignmentType,
      alignmentAlgorithm: alignmentAlgorithm,
      gapOpenPenalty: gapOpenPenalty,
      gapExtendPenalty: gapExtensionPenalty,
      calculateSSAA: calculateSSAA,
      gormodel: gorModel
    }, function(data, textStatus) {
      //Replace the progress bar by the data
      dialog.empty();
      dialog.append(data);
	$(".button").button();
    });
}

//Currently unused
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
  if(!model) {
    alert("Please select a GOR model!");
    return;
  }
  
  if(!seqId && !gor5Alignment) {
    alert("Either a sequence or a GOR5 alignment needs to be filled!");
    return;
  }
  var probabilities = ($("input[name=probabilities]:checked").attr("checked") ? 1 : 0);
  var avgPost = ($("input[name=avgPost]:checked").attr("checked") ? 1 : 0);
  var stdPost = ($("input[name=stdPost]:checked").attr("checked") ? 1 : 0);
  
  var avgProb = $("#avgProb").val();
  var stdProb = $("#stdProb").val();
  
  var id = "ssp-" + new Date().getTime(); //Allow same alignment (e.g. with different params) multiple times
  id = id.replace(/:/g,"-");
  $("#" + id).remove();
  $("#dialogsContainer").append('<div id="' + id + '" style="display: none;"></div>')
  var dialog = $("#" + id);
  dialog.attr("title","GOR Secondary Structure Prediction");
  dialog.dialog({autoOpen: false,modal: false,bgiframe: true,width:1200,height:500});
  //Show the progress bar & dialog
  dialog.empty();
  dialog.append("<div id=\"alignmentProgressBar\"></div>");
  dialog.progressbar({
      value: false
    });
  dialog.dialog({autoOpen: false,modal: false,bgiframe: true,width:1500,height:750});
  dialog.dialog("open");
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
    dialog.empty();
    dialog.append(data);
    dialog.append('<div class="button" onclick="showValiSSP(\'test\')">Validate</div>');
	$(".button").button();
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

function showValiAli(id) {
    //Get all the field values
  var reference = $("reference-" + id).val();
  var alignment = $("aliFile-" + id).val();
  //Show the progress bar & dialog
  $("#validateDialog").empty();
  $("#validateDialog").append("<div id=\"alignmentProgressBar\"></div>");
  $("#validateDialog").progressbar({
      value: false
    });
  $("#validateDialog").dialog({autoOpen: false,modal: false,bgiframe: true,width:1000,height:750});
  $('#validateDialog').dialog('open');
  //
  $.post("validation/validate_ali.cgi", {
    alignment: alignment,
    reference: reference
  }, function(data, textStatus) {
    //Replace the progress bar by the data
    $("#validateDialog").empty();
    $("#validateDialog").append(data);
  });
}


function showValiSSP(id) {
    //Get all the field values
  //var reference = $("reference-" + id).val();
  //var alignment = $("aliFile-" + id).val();
  //Show the progress bar & dialog
  $("#validateDialog").empty();
  $("#validateDialog").append("<div id=\"alignmentProgressBar\"></div>");
  $("#validateDialog").progressbar({
      value: false
    });
  $("#validateDialog").dialog({autoOpen: false,modal: false,bgiframe: true,width:1000,height:750});
  $('#validateDialog').dialog('open');
  //
  $.post("validation/validate_ali.cgi", {
    //alignment: alignment,
    //reference: reference
  }, function(data, textStatus) {
    //Replace the progress bar by the data
    $("#validateDialog").empty();
    $("#validateDialog").append("<img src=\"ssp/out.png\"></img>");
  });
}


function addSequence(id, name, type) {
  var sequences = $.jStorage.get("sequences", []);
  sequences.push({id: id, name: name, type: type});
  var sequences = $.jStorage.set("sequences", sequences);
  renderSequences();
}


function clearSequences() {
  renderSequences();
}
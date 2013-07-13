<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" />

	<xsl:template match="/">
		<html>
			<head>
				<title>History visualisation with InfoVis</title>
				
				<!-- CSS Files -->
				<link type="text/css" href="file:///android_asset/history_base.css" rel="stylesheet" />
				<link type="text/css" href="file:///android_asset/forceDirected.css" rel="stylesheet" />
				
				<!-- JIT Library File -->
				<script language="javascript" type="text/javascript" src="file:///android_asset/jit.js"></script>
									
				<script type="text/javascript">
				
					var labelType, useGradients, nativeTextSupport, animate;

					(function() {
  						var ua = navigator.userAgent,
      					iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
      					typeOfCanvas = typeof HTMLCanvasElement,
      					nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
      					textSupport = nativeCanvasSupport && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
      					
  						//I'm setting this based on the fact that ExCanvas provides text support for IE
  						//and that as of today iPhone/iPad current text support is lame
  						labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
						nativeTextSupport = labelType == 'Native';
						useGradients = nativeCanvasSupport;
						animate = !(iStuff || !nativeCanvasSupport);
					})();

					var Log = {
  						elem: false,
  						write: function(text){
    						if (!this.elem) 
      							this.elem = document.getElementById('log');
    						this.elem.innerHTML = text;
    						this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
  						}
					};
					
					function init(){

				  	// init ForceDirected
				  	var fd = new $jit.ForceDirected({
				    		//id of the visualization container
				    		injectInto: 'infovis',
						    //Enable zooming and panning
						    //by scrolling and DnD
						    Navigation: {
						      enable: true,
						      //Enable panning events only if we're dragging the empty
						      //canvas (and not a node).
						      panning: 'avoid nodes',
						      zooming: 10 //zoom speed. higher is more sensible
						    },
				    // Change node and edge styles such as
				    // color and width.
				    // These properties are also set per node
				    // with dollar prefixed data-properties in the
				    // JSON structure.
				    Node: {
				      overridable: true,
					  color: "#D6D6D6",
				      $type: "circle",
				      dim: 5
				    },
				    Edge: {
				      overridable: true,
				      color: 'white',
				      lineWidth: 1.0
				    },
				    //Native canvas text styling
				    Label: {
				      type: labelType, //Native or HTML
				      size: 10,
				      style: 'bold',
					  color: '#757575'
				    },
				    //Add Tips
				    Tips: {
				      enable: true,
				      onShow: function(tip, node) {
				        //count connections
				        var count = 0;
				        node.eachAdjacency(function() { count++; });
				        //display node info in tooltip
				        tip.innerHTML = "<div class="tip-title">" + node.name + "</div>"
				          + "<div class="tip-text"><b>connections:</b> " + count + "</div>";
				      }
				    },
				    // Add node events
				    Events: {
				      enable: true,
				      type: 'Native',
				      //Change cursor style when hovering a node
				      onMouseEnter: function() {
				        fd.canvas.getElement().style.cursor = 'move';
				      },
				      onMouseLeave: function() {
				        fd.canvas.getElement().style.cursor = '';
				      },
				      //Update node positions when dragged
				      onDragMove: function(node, eventInfo, e) {
				          var pos = eventInfo.getPos();
				          node.pos.setc(pos.x, pos.y);
				          fd.plot();
				      },
				      //Implement the same handler for touchscreens
				      onTouchMove: function(node, eventInfo, e) {
				        $jit.util.event.stop(e); //stop default touchmove event
				        this.onDragMove(node, eventInfo, e);
				      },
				      //Add also a click handler to nodes
				      onClick: function(node) {
				        if(!node) return;
				        // Build the right column relations list.
				        // This is done by traversing the clicked node connections.
				        var html = "<h4>" + node.name + "</h4><b> connections:</b><ul><li>",
				            list = [];
				        node.eachAdjacency(function(adj){
				          list.push(adj.nodeTo.name);
				        });
				        //append connections information
				        $jit.id('inner-details').innerHTML = html + list.join("</li><li>") + "</li></ul>";
				      }
				    },
				    //Number of iterations for the FD algorithm
				    iterations: 200,
				    //Edge length
				    levelDistance: 75,
				    // Add text to the labels. This method is only triggered
				    // on label creation and only for DOM labels (not native canvas ones).
				    onCreateLabel: function(domElement, node){
				      domElement.innerHTML = node.name;
				      var style = domElement.style;
				      style.fontSize = "0.8em";
				      style.color = "#ddd";
				    },
				    // Change node styles when DOM labels are placed
				    // or moved.
				    onPlaceLabel: function(domElement, node){
				      var style = domElement.style;
				      var left = parseInt(style.left);
				      var top = parseInt(style.top);
				      var w = domElement.offsetWidth;
				      style.left = (left - w / 2) + 'px';
				      style.top = (top + 10) + 'px';
				      style.display = '';
				    }
				  });
				  
				  
				  fd.graph = new $jit.Graph(fd.graphOptions, fd.config.Node, fd.config.Edge, fd.config.Label);
				  
				  //Create main node
					var rootNode = { id: "root", name: "Test"}
					fd.root = rootNode.id;
					fd.graph.addNode(rootNode);
				
					var Zaehler = 0;
					while (Zaehler <= 5) {
						var idTest = "testing " + Zaehler;
						var labelText = "Test_Punkt " + Zaehler;
						var node = {id: idTest.toString(), name: labelText.toString(), data: { "$color": get_random_color()} }
						
						//Create Nodes -- connect them to main node for now
						fd.graph.addNode(node);		
						fd.graph.addAdjacence(rootNode, node, {});
						
						Zaehler++;
					}
				
					//Display graph
					fd.refresh();
					
					function get_random_color() {
						var letters = '0123456789ABCDEF'.split('');
						var color = '#';
						for (var i = 0; i < 6; i++ ) {
							color += letters[Math.round(Math.random() * 15)];
						}
				    	return color;
					}
				}
					
				</script>
			</head>
			<body onload="init();" border="0">
				<div id="container">
					<div id="center-container">
    					<div id="infovis"></div>    
					</div>
					<div id="log"></div>
				</div>
			</body>
		</html>	
	</xsl:template>
</xsl:stylesheet>
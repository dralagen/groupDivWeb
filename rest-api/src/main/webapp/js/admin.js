var divergencesValues = 
	[
		{data: [[Date.parse("2015-12-12T12:12:12"), 20],[Date.parse("2015-12-12T12:14:12"), 50], [Date.parse("2015-12-12T12:15:12"), 70], [Date.parse("2015-12-12T12:16:12"), 30]], label: "GDTot"},
		{data: [[Date.parse("2015-12-12T12:12:12"), 1],[Date.parse("2015-12-12T12:14:12"), 2], [Date.parse("2015-12-12T12:15:12"), 3], [Date.parse("2015-12-12T12:16:12"), 4]],  label: "user1"},
		{data: [[Date.parse("2015-12-12T12:12:12"), 100],[Date.parse("2015-12-12T12:14:12"), 200], [Date.parse("2015-12-12T12:15:12"), 300], [Date.parse("2015-12-12T12:16:12"), 400]],  label: "user2"}
	];
var plotStep, plotCourbe;

$(document).ready(function(){
	plotStep = $.plot("#stepGraph", 

		divergencesValues,
	{
		xaxis: {
			mode: "time",
			timeformat: "%Y-%m-%d %H:%M:%S"
			
		},
		legend: {
			show: true,
			position: "ne",
			margin:[-60, 0],
			noColumns: 1,
		},
		series: {
			lines: {
				show: true,
				steps:true,
			}
		}
	}
	);
	
		plotCourbe = $.plot("#courbe", 

		divergencesValues,
	{
		xaxis: {
			mode: "time",
			timeformat: "%Y-%m-%d %H:%M:%S"
		},
		legend: {
			show: true,
			position: "ne",
			margin:[-60, 0],
			noColumns: 1,
		}
	}
	);
	// Add the Flot version string to the footer

$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
});


	

function addDivergenceForUsers(){
	//TODO get the group divergence for all the users
}

function getUsers(){
	//TODO ask server to know the name of all the users
}

function updateGraph(){
	//TODO put in dataAndLabelForGraph the data of all users
	plotStep.setData(dataAndLabelForGraph);
	plotStep.setupGrid();
	plotStep.draw();
}

function fonctionQuiSexecuteToutesLesDeuxSecindes(){
	// traitement
	//alert("oui");
	//setTimeout(tafonction,2000); /* rappel après 2 secondes = 2000 millisecondes */
}
 
//tafonction();

(function(){
	var app = angular.module("Admin", []);
	
	app.controller("AdminController", function(){
		this.tab = 1;

		
		this.users = [{value: "GDTot", s:"GDTot"}, {value :"user1", s:"user1"}, {value:"user2", s:"user2"}];
		
		this.selectTab = function(setTab){
			this.tab = setTab;
		};
		
		this.visibilityStepGraph = function(bool){
			plotStep.resize();
			plotStep.setupGrid();
			plotStep.draw();
		};
		
		this.visibilityCourbe = function(bool){
			plotCourbe.resize();
			plotCourbe.setupGrid();
			plotCourbe.draw();
		};
		
		this.voirDivergence = function(b, n){
			for(i in plotStep.getData()){
				data = plotStep.getData();
				if(data[i].label == n){
					data[i].lines.show = !data[i].lines.show;
					plotStep.setData(data);
					
					(plotCourbe.getData())[i].lines.show = !(plotCourbe.getData())[i].lines.show;
				}
			}
			plotStep.setupGrid();
			plotStep.draw();
			
			
			plotCourbe.setupGrid();
			plotCourbe.draw();
		};
		
		this.getData = function(u){
			var temp=[];
			for(i in plotStep.getData()){
				data = plotStep.getData();

				if(data[i].label == u){
					temp = data[i].data;
				}
			}
			
			return temp;			
		};
	});
	
})();

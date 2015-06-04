var divergencesValues = [{data: [], lines: { show: true, steps: true }, label: "GDTot"}];
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

(function(){
	var app = angular.module("Admin", []);
	
	app.controller("AdminController", function(){
		this.tab = 1;

		
		this.users = ["GDTot", "user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "user10", "user11", "user12", "user13", "user14", "user15", "user16"];
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
	});
	
})();

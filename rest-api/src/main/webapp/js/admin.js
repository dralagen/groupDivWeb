var divergencesValues = [{data: [], lines: { show: true, steps: true }, label: "GDTot"}];

$(document).ready(function(){
	plot = $.plot("#placeholder", 

		divergencesValues,
	{
		xaxis: {
			mode: "time",
			timeformat: "%Y-%m-%d %H:%M:%S"
		},
		legend: {
			show: true,
			container: $("#legend"),
			noColumns: 1,
		}
	}
);
});

// Add the Flot version string to the footer

$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
	

function addDivergenceForUsers(){
	//TODO get the group divergence for all the users
}

function getUsers(){
	//TODO ask server to know the name of all the users
}

function updateGraph(){
	//TODO put in dataAndLabelForGraph the data of all users
	plot.setData(dataAndLabelForGraph);
	plot.setupGrid();
	plot.draw();
}


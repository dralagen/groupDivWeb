var app = angular.module("groupDiv.adminController", []);

	app.controller("adminController",['$scope', '$http', function($scope, $http){
	/*	//https://groupdivxp.appspot.com/_ah/api/groupDivWeb/v1/session/'+$scope.selectedSession
		var ROOT = 'https://groupdivxp.appspot.com/_ah/api';
			gapi.client.load('groupDivWeb', 'v1', function() {
			doSomethingAfterLoading();
		}, ROOT);*/
		

		$scope.sessionChoosen = false;
		$scope.useGroupDiv = true;
		$scope.sessions = [{name: "Session one",id: "5639445604728832"}, {name: "Session Two",id: "5639445604238832"}];
		console.log("coucou");
		
		$scope.users = [];

		this.divergencesValues = [
			{data: [[Date.parse("2015-12-12T12:12:12"), 20],[Date.parse("2015-12-12T12:14:12"), 50], [Date.parse("2015-12-12T12:15:12"), 70], [Date.parse("2015-12-12T12:16:12"), 30]], label: "GDTot"},
			{data: [[Date.parse("2015-12-12T12:12:12"), 1],[Date.parse("2015-12-12T12:14:12"), 2], [Date.parse("2015-12-12T12:15:12"), 3], [Date.parse("2015-12-12T12:16:12"), 4]],  label: "usr1"},
			{data: [[Date.parse("2015-12-12T12:12:12"), 100],[Date.parse("2015-12-12T12:14:12"), 200], [Date.parse("2015-12-12T12:15:12"), 300], [Date.parse("2015-12-12T12:16:12"), 400]],  label: "usr2"}
		];
		
		//$http.get('https://groupdivxp.appspot.com/_ah/api/groupDivWeb/v1/session?fields=items(key%2Cname)').
			//success(function(data) {
				//for(x of data.items){
					//temp = {name: x.name, id: x.key.id};
					//$scope.sessions.push({name: "Session one",id: "5639445604728832"})
			//});

	/*	
		this.plotStep = $.plot(
			"#stepGraph", 
			this.divergencesValues,
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
	
		this.plotCurve = $.plot(
			"#curve", 
			this.divergencesValues,
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

		$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");


		this.addDivergenceForUsers = function(){
			//TODO get the group divergence for all the users
		};

		this.getUsers = function(){
			//TODO ask server to know the name of all the users
		};

		this.updateGraph = function(){
			//TODO put in dataAndLabelForGraph the data of all users
			plotStep.setData(dataAndLabelForGraph);
			plotStep.setupGrid();
			plotStep.draw();
		};
		
		this.visibilityStepGraph = function(bool){
			this.plotStep.resize();
			this.plotStep.setupGrid();
			this.plotStep.draw();
		};
		
		this.visibilityCurve = function(bool){
			this.plotCurve.resize();
			this.plotCurve.setupGrid();
			this.plotCurve.draw();
		};
		
		this.watchDivergence = function(n){
			for(i in this.plotStep.getData()){
				data = this.plotStep.getData();
				if(data[i].label == n){
					data[i].lines.show = !data[i].lines.show;
					this.plotStep.setData(data);
					(this.plotCurve.getData())[i].lines.show = !(this.plotCurve.getData())[i].lines.show;
				}
			}
			this.plotStep.setupGrid();
			this.plotStep.draw();
			
			this.plotCurve.setupGrid();
			this.plotCurve.draw();
		};
		*/
		$scope.chooseSession = function(){
			if(!angular.isUndefined($scope.selectedSession)){
				$scope.sessionChoosen = true;
				$http.get('https://groupdivxp.appspot.com/_ah/api/groupDivWeb/v1/session/'+$scope.selectedSession).
					success(function(data) {
					$scope.useGroupDiv = data.withGroupDiv;
					temp = {name: "GDTot", ue: "no ue", div: data.gdtot, s:"GDTot"};
					$scope.users.push(temp);
					for(info of data.ues){
						temp = {name: info.author.name, ue: info.title, div:0, s:info.author.name};
						$scope.users.push(temp);
					}
				});

			}
		}
		/*
		this.getData = function(u){
			var a=-1;
			data = this.plotStep.getData();
			for(i in data){				
				if(data[i].label == u){
					a = i;
				}
			}
			if(a === -1){
				return [];
			}
			else{
				return data[a].data;
			}
		};
*/
	}]);		

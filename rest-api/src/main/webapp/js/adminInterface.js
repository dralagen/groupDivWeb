var app = angular.module("groupDiv.adminController", []);

	app.controller("adminController",['$scope', 'GApi', function($scope, GApi){


		$scope.sessionChoosen = false;

		$scope.useGroupDiv = true;
		$scope.sessions = [];

		$scope.users = [];

		$scope.divergencesValues = [
			{data: [[Date.parse("2015-12-12T12:12:12"), 20],[Date.parse("2015-12-12T12:14:12"), 50], [Date.parse("2015-12-12T12:15:12"), 70], [Date.parse("2015-12-12T12:16:12"), 30]], label: "GDTot"},
			{data: [[Date.parse("2015-12-12T12:12:12"), 1],[Date.parse("2015-12-12T12:14:12"), 2], [Date.parse("2015-12-12T12:15:12"), 3], [Date.parse("2015-12-12T12:16:12"), 4]],  label: "usr1"},
			{data: [[Date.parse("2015-12-12T12:12:12"), 100],[Date.parse("2015-12-12T12:14:12"), 200], [Date.parse("2015-12-12T12:15:12"), 300], [Date.parse("2015-12-12T12:16:12"), 400]],  label: "usr2"}
		];


		GApi.execute('groupDivWeb', 'session.list').then(
			function(data) {
				data = {
					items :{
						item:{
							name:"oui",
							key:{
								id:"pm"
							}
						}
					}
				};
				angular.forEach(data.items, function(x){
					temp = {name: x.name, id: x.key.id};
					$scope.sessions.push(temp);
				});
			},
			function() {
				console.log("error :(");
			}
		);


		$scope.init_plot = function(){
			console.log("init des plot");
			$scope.plotStep = $.plot(
				"#stepGraph",
				$scope.divergencesValues,
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

			$scope.plotCurve = $.plot(
				"#curve",
				$scope.divergencesValues,
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
		}

		$scope.addDivergenceForUsers = function(){
			//TODO get the group divergence for all the users
		};

		$scope.getUsers = function(){
			//TODO ask server to know the name of all the users
		};

		$scope.updateGraph = function(){
			//TODO put in dataAndLabelForGraph the data of all users
			plotStep.setData(dataAndLabelForGraph);
			plotStep.setupGrid();
			plotStep.draw();
		};

		$scope.visibilityStepGraph = function(bool){
			$scope.plotStep.resize();
			$scope.plotStep.setupGrid();
			$scope.plotStep.draw();
		};

		$scope.visibilityCurve = function(bool){
			$scope.plotCurve.resize();
			$scope.plotCurve.setupGrid();
			$scope.plotCurve.draw();
		};

		$scope.watchDivergence = function(n){
			for(i in $scope.plotStep.getData()){
				data = $scope.plotStep.getData();
				if(data[i].label == n){
					data[i].lines.show = !data[i].lines.show;
					$scope.plotStep.setData(data);
					($scope.plotCurve.getData())[i].lines.show = !($scope.plotCurve.getData())[i].lines.show;
				}
			}
			$scope.plotStep.setupGrid();
			$scope.plotStep.draw();

			$scope.plotCurve.setupGrid();
			$scope.plotCurve.draw();
		};

		$scope.chooseSession = function(){
			if(!angular.isUndefined($scope.selectedSession)){
				$scope.sessionChoosen = true;
				GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.selectedSession}).then(
					function(data){
						$scope.useGroupDiv = data.withGroupDiv;
						temp = {name: "GDTot", ue: "no ue", div: data.gdtot, s:"GDTot"};
						$scope.users.push(temp);

						angular.forEach(data.ues, function(info){
							temp = {name: info.author.name, ue: info.title, div:0, s:info.author.name};
							$scope.users.push(temp);

						});
					}, function(){
						console.log("error");
					}
				);

			}
		}

		$scope.getData = function(u){
			var a=-1;
			data = $scope.plotStep.getData();
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

	}]);

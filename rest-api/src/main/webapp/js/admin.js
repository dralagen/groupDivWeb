var app = angular.module("groupDiv.adminController", []);


app.controller("adminController",['$scope', 'GApi', '$location', '$interval', function myC($scope, GApi, $location, $interval){

	$scope.sessionChoosen = false;

	$scope.hostLink = $location.protocol() + '://'+ $location.host() +':'+  $location.port() + "/index.html#/user/";

	$scope.useGroupDiv = true;
	$scope.sessions = [];

	$scope.selectedUE = {};
	$scope.selectedUE.sel = [];

	$scope.selectedUser = {};
	$scope.selectedUser.sel = {};

	$scope.reviews = {};

	$scope.users = [];
	$scope.ues = [];

	$scope.maxDate = 0;

	$scope.refresh = {};
	$scope.refresh.manualRefreshLog = false;

	var interval = null;

	$scope.dataAndLabelForGraph = [];

	GApi.execute('groupDivWeb', 'session.list').then(
		function(data){
			console.log("list sessions ok ");
			angular.forEach(data.items, function(item){
				temp = {name: item.name, id: item.id};
				$scope.sessions.push(temp);
			});
		},
		function(err){
			console.log("error : we can't get the list of sessions : " + err.error.message);
		}
	);


	$scope.chooseSession = function(){
		if(!angular.isUndefined($scope.selectedSession)){
			$scope.sessionChoosen = true;

			GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.selectedSession}).then(
				function(data){
					console.log("session loaded");
					$scope.useGroupDiv = data.withGroupDiv;
					//get users
					$scope.users = data.user;
					$scope.users.push({name: "GDTot", id:"GDTot"});

					angular.forEach($scope.users, function(user){
						temp = {data: [], label: user.id,  lines: {show: true}};
						$scope.dataAndLabelForGraph.push(temp);

					});

					$scope.selectedUser.sel = $scope.users[0];
					console.log("we get the users");

					//get ues
					angular.forEach(data.ue, function(oneUe){
						$scope.ues.push(oneUe);
						$scope.reviews[oneUe.id] = [];
					});
					$scope.selectedUE.sel = $scope.ues[0];
					$scope.pullTheUser();
					console.log("we get the ues");

					interval = $interval( function(){$scope.updateLog(); }, 3000);

				}, function(err){
					console.log("error : we can't load the session : " + err.error.message);
				}
			);

			GApi.execute('groupDivWeb', 'divergence.list', {sessionId: $scope.selectedSession}).then(
				function(data){
					console.log("list divergences ok ");
					angular.forEach(data.items, function(item){
						angular.forEach($scope.dataAndLabelForGraph, function(divValue){
							if(angular.isUndefined(item.userDivergence[divValue.label])){
								temp = [Date.parse(item.time), parseInt(item.globalDivergence)];
								$scope.maxDate = Math.max($scope.maxDate, Date.parse(item.time));
								divValue.data.push(temp);
							}
							else {
								temp = [Date.parse(item.time), parseInt(item.userDivergence[divValue.label])];
								divValue.data.push(temp);
							}
						});
					});
				},
				function(err){
					console.log("error : we can't get the list of divergences : " + err.error.message);
				}
			);
		}
	}

	$scope.pullTheUser = function(){
		GApi.execute('groupDivWeb', 'action.pull', {sessionId: $scope.selectedSession, fromUserId: $scope.selectedUser.sel.id, toUserId: $scope.selectedUser.sel.id}).then(
			function(resp) {
				console.log("pull on : " + $scope.selectedUser.sel.id + " successful");
				//get ues content
				angular.forEach(resp.ue, function(item){
					angular.forEach($scope.ues, function(ue){
						if(ue.id == item.id){
							ue.content = item.content;
						}
					});
				});
				console.log("we get the versions of ues");

				//get reviews
				angular.forEach($scope.reviews, function(rev){
					rev.splice(0, rev.length);
				});
				angular.forEach(resp.review, function(rev){
					$scope.reviews[rev.ueId].push(rev);
				});
				console.log("we get the new reviews");
			}, function(err) {
				console.log("error you can't pull : " + $scope.selectedUser.sel.id + " : " + err.error.message);
			}
		);
	}

	$scope.init_plot = function(){
		$scope.plotStep = $.plot(
			"#stepGraph",
			$scope.dataAndLabelForGraph,
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
					labelFormatter: function(label, series) {
						// series is the series object for the label
						var result = "";
						for(i in $scope.users){
							if($scope.users[i].id == label){
								result = $scope.users[i].name;
							}
						}
						return result;
					},
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
			$scope.dataAndLabelForGraph,
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
					labelFormatter: function(label, series) {
						// series is the series object for the label
						var result = "";
						for(i in $scope.users){
							if($scope.users[i].id == label){
								result = $scope.users[i].name;
							}
						}
						return result;
					},
				},
				series: {
					lines: {
						show: true,
						steps:false,
					}
				}
			}
		);
		$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
	}

	$scope.reloadDataInGraph = function(){
		$scope.plotStep.setData($scope.dataAndLabelForGraph);
		$scope.plotCurve.resize();
		$scope.plotStep.setupGrid();
		$scope.plotStep.draw();

		$scope.plotCurve.setData($scope.dataAndLabelForGraph);
		$scope.plotCurve.resize();
		$scope.plotCurve.setupGrid();
		$scope.plotCurve.draw();
	}

	$scope.updateGraph = function(){
		GApi.execute('groupDivWeb', 'divergence.list', {sessionId: $scope.selectedSession}).then(
			function(data){
				var newMaxDate = 0;
				angular.forEach(data.items, function(item){
					if(Date.parse(item.time)>$scope.maxDate) {
						newMaxDate = Math.max(newMaxDate,item.time );
						angular.forEach($scope.dataAndLabelForGraph, function (divValue) {
							if (angular.isUndefined(item.userDivergence[divValue.label])) {
								temp = [Date.parse(item.time), parseInt(item.globalDivergence)];
								divValue.data.push(temp);
							}
							else {
								temp = [Date.parse(item.time), parseInt(item.userDivergence[divValue.label])];
								divValue.data.push(temp);
							}
						});
					}
					$scope.reloadDataInGraph();
				});
				$scope.maxDate = Math.max(newMaxDate, $scope.maxDate);
			},
			function(err){
				console.log("error : we can't get the list of divergences : " + err.error.message);
			}
		);
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

	$scope.watchDivergence = function(userId){
		for(i in $scope.dataAndLabelForGraph){
			if($scope.dataAndLabelForGraph[i].label == userId){
				$scope.dataAndLabelForGraph[i].lines.show = !$scope.dataAndLabelForGraph[i].lines.show;
			}
		}
		$scope.reloadDataInGraph();
	};


	$scope.getData = function(user){
		var a=-1;
		data = $scope.plotStep.getData();
		for(i in data){
			if(data[i].label == user){
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

	$scope.updateLog = function() {
		$scope.updateGraph();
	}

	$scope.setManualRefresh = function(){
		$scope.refresh.manualRefreshLog = !$scope.refresh.manualRefreshLog;
		if($scope.refresh.manualRefreshLog == false){
			interval = $interval( function(){$scope.updateLog(); }, 3000);
		}else{
			$interval.cancel(interval);
		}
	}
}]);

var app = angular.module("groupDiv.adminController", []);


app.controller("adminController",['$scope', 'GApi','$location', function myC($scope, GApi, $location){


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

	$scope.divergencesValues = [
		{data: [[Date.parse("2015-12-12T12:12:12"), 20],[Date.parse("2015-12-12T12:14:12"), 50], [Date.parse("2015-12-12T12:15:12"), 70], [Date.parse("2015-12-12T12:16:12"), 30]], label: "GDTot"},
		{data: [[Date.parse("2015-12-12T12:12:12"), 1],[Date.parse("2015-12-12T12:14:12"), 2], [Date.parse("2015-12-12T12:15:12"), 3], [Date.parse("2015-12-12T12:16:12"), 4]],  label: "usr1"},
		{data: [[Date.parse("2015-12-12T12:12:12"), 100],[Date.parse("2015-12-12T12:14:12"), 200], [Date.parse("2015-12-12T12:15:12"), 300], [Date.parse("2015-12-12T12:16:12"), 400]],  label: "usr2"}
	];

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

				}, function(err){
					console.log("error : we can't load the session : " + err.error.message);
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
						console.log(ue.id == item.id);
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
				console.log("error you can't pull : " + userId + " : " + err.error.message);
			}
		);
	}

	$scope.init_plot = function(){
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

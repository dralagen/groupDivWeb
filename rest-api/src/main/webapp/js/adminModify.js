var app = angular.module('groupDiv.modifySession', []);

app.controller('modifySession', ['$scope','GApi' , '$translate', function($scope, GApi, $translate){
	$scope.sessionChoosen = false;

	$scope.old = {};
	$scope.old.useGroupDiv = true;
	$scope.old.sessionName = "";

	$scope.numberOfNewUes = 0;
	$scope.new_ = {};
	$scope.new_.useGroupDiv = true;
	$scope.new_.sessionName = "";

	$scope.sessions = [];
	$scope.ueFocus = {};

	$scope.ues = [];

	$scope.alerts = [];

	GApi.execute('groupDivWeb', 'session.list').then(
		function(data){
			console.log("list sessions ok ");
			angular.forEach(data.items, function(item){
				temp = {name: item.name, id: item.id};
				$scope.sessions.push(temp);
			});
		},
		function(err){
			console.log("error : we can't get the list of sessions : ");
			console.log(err.error.message);
		}
	);

	$scope.deleteUe = function (ue) {

		var index = $scope.ues.indexOf(ue);
		$scope.ues.splice(index, 1);

		if(!angular.isUndefined(ue.id)){
			GApi.execute('groupDivWeb', 'session.ue.delete', {sessionId: $scope.selectedSession, ueId: ue.id}).then(
				function(data){
					console.log("ue : " + ue.id + " deleted");
					$scope.alerts.push({type: 'success', msg: "Congratulation, UE and user deleted!"});

				},
				function(err){
					console.log("ue : " + ue.id + " not deleted ");
					console.log(err.error.message);

					$scope.alerts.push({type: 'warning', msg: $translate.instant(err.error.message)});
				}
			);
		}
	};

	$scope.addUe = function () {
		var ue = {
			userName: '',
			title: '',
		}
		$scope.ues.push(ue);
	};

	$scope.modifySession = function(){

		//ue to edit
		angular.forEach($scope.ues, function(ue){
			if(!angular.isUndefined(ue.id) && ue.change == true){
				GApi.execute('groupDivWeb', 'session.ue.edit', {sessionId: $scope.selectedSession, ueId: ue.id, title: ue.title, user: ue.userName}).then(
					function(data){
						console.log("ue : " + ue.id + " is modify");
						$scope.alerts.push({type: 'success', msg: "Congratulation, UEs edited!"});

					},
					function(err){
						console.log("ue : " + ue.id + " is not modify : ");
						console.log(err.error.message);

						$scope.alerts.push({type: 'warning', msg: $translate.instant(err.error.message)});
					}
				);
			}
		});

		//modify session name and withGD
		if(	$scope.old.useGroupDiv != $scope.new_.useGroupDiv || $scope.old.sessionName != $scope.new_.sessionName){
			GApi.execute('groupDivWeb', 'session.edit', {sessionId: $scope.selectedSession, name: $scope.new_.sessionName, withGroupDiv: $scope.new_.useGroupDiv}).then(
				function(data){
					console.log("modify session name and withGD ok ");
					$scope.alerts.push({type: 'success', msg: "Congratulation, Session modified!"});

				},
				function(err){
					console.log("error : we can't modify session name and withGD : ");
					console.log(err.error.message);

					$scope.alerts.push({type: 'warning', msg: $translate.instant(err.error.message)});
				}
			);
		}

	}

	$scope.chooseSession = function(){
		if(!angular.isUndefined($scope.selectedSession)){
			$scope.sessionChoosen = true;

			GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.selectedSession}).then(
				function(data){
					console.log("session loaded");
					$scope.old.useGroupDiv = data.withGroupDiv;
					$scope.old.sessionName = data.name;

					$scope.new_.useGroupDiv = data.withGroupDiv;
					$scope.new_.sessionName = data.name;

					$scope.useGroupDiv = data.withGroupDiv;

					$scope.ues = data.ue;

					angular.forEach(data.user, function(usr){
						angular.forEach($scope.ues, function(ue){
							if(usr.id == ue.authorId){
								ue.userName = usr.name;
							}
						});
					});

					angular.forEach($scope.ues, function(ue){
						ue.change = false;
					});

					$scope.sessionName = data.name;
				}, function(){
					console.log("error : we can't load the session : ");
					console.log(err.error.message);

				}
			);
		}
	};

	$scope.updateUE = function(oneUE){
		if(!angular.isUndefined(oneUE.id)) {
			oneUE.change = true;
		}
	}

	$scope.quitInput = function(ue){
		if(ue.title != "" && ue.userName!="" && !angular.isUndefined(ue.id)){
			GApi.execute('groupDivWeb', 'session.ue.add', {sessionId: $scope.selectedSession, title:ue.title, user: ue.userName}).then(
				function(data){
					console.log("ue : " + ue.title + "  and " + ue.userName + " are added");
					$scope.alerts.push({type: 'success', msg: "Congratulation, UE and user added!"});
					ue.id = data.id;
					ue.change = false;
				},
				function(err){
					console.log("ue : " + ue.title + "  and " + ue.userName + " are not added : ");
					console.log(err.error.message);

					$scope.alerts.push({type: 'warning', msg: $translate.instant(err.error.message)});
				}
			);
		}
	}

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

}]);

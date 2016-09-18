angular.module('app.component1').controller('ChallengeController', ['$scope', '$modal', 'ChallengeService', 'PlayerProfileService',
    function($scope, $modal, ChallengeService, PlayerProfileService) {
        'use strict';

        var getAllChallenges, openErrorModal;

        $scope.data = {
            form: {},
            isSentChallengesAvailable: false,
            isReceivedChallengesAvailable: false,
            sentChallenges: [],
            receivedChallenges: [],
            idProfileCurrentPlayer: null,
            playerProfileAndStatistics: null,
            playersMatching: [],
            isMatchingPlayersAvailable: false,
            loginToFind: null,
            foundPlayersByLogin: [],
            isFoundPlayersByLoginAvailable: false
        };

        $scope.getSentChallenges = function() {
            ChallengeService.getSentChallenges($scope.data.playerProfileAndStatistics.idPlayerStatistics).then(function(response) {
                $scope.data.sentChallenges = response.data;
            }).then(function() {
                if ($scope.data.sentChallenges.length === 0) {
                    $scope.data.isSentChallengesAvailable = false;
                    return;
                };
                $scope.data.isSentChallengesAvailable = true;
            }, function errorCallback(errorResponse) {
                $scope.data.isSentChallengesAvailable = false;
                openErrorModal(errorResponse);
            });
        };

        $scope.getReceivedChallenges = function() {
            ChallengeService.getReceivedChallenges($scope.data.playerProfileAndStatistics.idPlayerStatistics).then(function(response) {
                $scope.data.receivedChallenges = response.data;
            }).then(function() {
                if ($scope.data.receivedChallenges.length === 0) {
                    $scope.data.isReceivedChallengesAvailable = false;
                    return;
                };
                $scope.data.isReceivedChallengesAvailable = true;
            }, function errorCallback(errorResponse) {
                $scope.data.isReceivedChallengesAvailable = false;
                openErrorModal(errorResponse);
            });
        };

        $scope.getPlayerProfileAndStatistics = function() {
            PlayerProfileService.getPlayerProfileAndStatistics($scope.data.idProfileCurrentPlayer).then(function(response) {
                $scope.data.playerProfileAndStatistics = response.data;
                getAllChallenges();
            }, function errorCallback(errorResponse) {
              openErrorModal(errorResponse);
            });
        };

        openErrorModal = function(response) {
          $modal.open({
              templateUrl: "/component-1/modal-dialog/error-modal.tpl.html",
              controller: "ErrorModalController",
              backdrop: true,
              keyboard: true,
              backdropClick: true,
              size: 'lg',
              resolve: {
                provideResponse: function() {
                  return response;
                }
              }
          });
        }

        $scope.setPlayerProfileId = function() {
          $scope.data.playersMatching = [];
          $scope.data.isMatchingPlayersAvailable = false;
          $scope.data.loginToFind = null;
          $scope.data.foundPlayersByLogin = [];
          $scope.data.isFoundPlayersByLoginAvailable = false;
            $scope.getPlayerProfileAndStatistics();
        };

        $scope.getPlayersByLogin = function() {
          PlayerProfileService.getPlayersByLogin($scope.data.loginToFind).then(function(response) {
            $scope.data.foundPlayersByLogin = response.data;
            if ($scope.data.foundPlayersByLogin.length === 0) {
              $scope.data.isFoundPlayersByLoginAvailable = false;
              return;
            }
            $scope.data.isFoundPlayersByLoginAvailable = true;
          }, function errorCallback(errorResponse) {
            openErrorModal(errorResponse);
          });
        };

        getAllChallenges = function() {
          $scope.getSentChallenges();
          $scope.getReceivedChallenges();
        };

        $scope.acceptChallenge = function(index) {
          ChallengeService.acceptChallenge($scope.data.receivedChallenges[index]).then(function() {
            $scope.getReceivedChallenges();
          }, function errorCallback(response) {
            openErrorModal(response);
            $scope.getReceivedChallenges();
          });
        };

        $scope.declineChallenge = function(index) {
          ChallengeService.declineChallenge($scope.data.receivedChallenges[index]).then(function() {
            $scope.getReceivedChallenges();
          }, function errorCallback(response) {
            openErrorModal(response);
            $scope.getReceivedChallenges();
          });
        };

        $scope.cancelChallenge = function(index) {
          ChallengeService.cancelChallenge($scope.data.sentChallenges[index]).then(function() {
            $scope.getSentChallenges();
          }, function errorCallback(response) {
            openErrorModal(response);
            $scope.getSentChallenges();
          });
        };

        $scope.setPlayersMatchingForAutomaticChallenge = function() {
          ChallengeService.getPlayersMatchingForAutomaticChallenge($scope.data.playerProfileAndStatistics.idPlayerStatistics).then(function(response) {
            $scope.data.playersMatching = response.data;
            if ($scope.data.playersMatching.length === 0) {
              return;
            };
            $scope.data.isMatchingPlayersAvailable = true;
          }, function errorCallback(errorResponse) {
            openErrorModal(errorResponse);
          });
        };

        $scope.createChallengeForMatchingPlayer = function(index) {
          ChallengeService.createChallenge($scope.data.playerProfileAndStatistics.idPlayerStatistics, $scope.data.playersMatching[index].idPlayerStatistics).then(function(response) {
            $scope.getSentChallenges();
          }, function errorCallback(errorResponse) {
            openErrorModal(errorResponse);
          })
        };

        $scope.createChallengeForCustomPlayer = function(index) {
          ChallengeService.createChallenge($scope.data.playerProfileAndStatistics.idPlayerStatistics, $scope.data.foundPlayersByLogin[index].idPlayerStatistics).then(function(response) {
            $scope.getSentChallenges();
          }, function errorCallback(errorResponse) {
            openErrorModal(errorResponse);
          });
        };

}]);

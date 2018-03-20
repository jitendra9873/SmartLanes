var EddystoneBeaconScanner = require('eddystone-beacon-scanner');
var request = require('request');

update = {}
transacted = []
passedThrough = ''

EddystoneBeaconScanner.on('found', function(beacon) {
	var aadharID = beacon['instance'];
	var lPlate = Buffer.from(beacon['namespace'], 'hex').toString('utf8');
	console.log("Aadhar: " + aadharID);
	console.log("Licence: " + lPlate);
	console.log("Namespace: " + beacon["namespace"]);

	if(transacted.indexOf(lPlate) > -1) {
		//DUPLICATE PACKET
	} 
	else {
		//CALL TRANSACTION API FOR THIS PKEY
		var url = 'http://127.0.0.1:8000/transact/' + aadharID +'/' + lPlate;
		request(url, function (error, response, body) {
		    if (!error && response.statusCode == 200) {
		        console.log(body);
		    }
		});
	}
	//SERVER WILL SEND SMS TO USER

	//ADD to transacted list
	var pkey = lPlate;
	transacted.push(lPlate);
});


EddystoneBeaconScanner.on('updated', function(beacon) {
  var lPlate = Buffer.from(beacon['namespace'], 'hex').toString('utf8');
  var pkey = lPlate;
  if(pkey != passedThrough) {
  	update[pkey] = update[pkey] || [];
  	update[pkey].push(beacon['distance']);
  }
});


function avgDistance() {
	keyDistance = [];
	var keys = Object.keys(update);
	for (var i = 0; i < keys.length; i++) {
		console.log("For key: " + keys[i] + "\t" + update[keys[i]]);
  		var distances = update[keys[i]];
  		var sum = distances.reduce((previous, current) => current += previous);
		var avg = sum / distances.length;
		var obj = {
			"key" : keys[i],
			"distance" : avg 
		};
		keyDistance.push(obj);
		update[keys[i]] = [];
	}
	console.log("Before KEY DISTANCE: \n", keyDistance);
	keyDistance.sort(function (a, b) {
    	return a.distance > b.distance;
	});
	keyDistance.sort();
	console.log("After KEY DISTANCE: \n", keyDistance);
	console.log("\n\n");

	//CALL RADAR
	var options = {
		uri: 'http://127.0.0.1:8000/radarAPI/',
		method: 'POST',
		json: {
			"result": keyDistance
		}
	};
	request(options, function (error, response, body) {});


	//CHECK IF DISTANCE IS WITHIN THRESHOLD
	//IF YES THEN RAISE ELECTRONIC ARM AND
	// passedThrough = keyDistance[key]; 
}


EddystoneBeaconScanner.on('lost', function(beacon) {
	var lPlate = Buffer.from(beacon['namespace'], 'hex').toString('utf8');
	var pkey = lPlate;

	var index = transacted.indexOf(pkey);
	if (index > -1) {
    	transacted.splice(index, 1);
	}
	delete update[pkey];
});


setInterval(avgDistance, 5000);
EddystoneBeaconScanner.startScanning(true);

const functions = require('firebase-functions');
var admin = require("firebase-admin");
var serviceAccount = require("./serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://pizzadelivery-d4ea0.firebaseio.com"
});

defaultAuth = admin.auth();
var database = admin.database();

exports.notifyStu=functions.database.ref('/jobs/{jobId}/details')
.onWrite(event=>{
 
  // console.log(snapshot.child('jobType').val());
  var snapshot= event.data;
  var payload={
      data:{
        type:snapshot.child('jobType').val(),
        vacancy:snapshot.child('jobVacancy').val()
      }
  }; 

  var dref = admin.database().ref("/users");
  dref.on("child_added", function(datasnapshot) {
      var data = datasnapshot.val();
      var fcm = data.fcm;
      var type = data.type;
      // console.log(fcm);&&
      if(fcm!==undefined && type==="Student"){
          fcm = Object.keys(fcm).map(key => Object.assign({}, { key: key }, fcm[key]))
          fcm.forEach(function(childSnapshot) {

              return admin.messaging().sendToDevice(childSnapshot.token, payload)
              .then(function (response) {
                  console.log("Successfully sent message:", response);
                  console.log("Send token",deviceId);
              })
              .catch(function (error) {
                  console.log("Error sending message:", error);
              });
          });
          
      }
      //var uid = datasnapshot.val();
      // var uid = datasnapshot.child('/fcm');
      // if(uid.type==="Student" && uid.token!== undefined ){
          
      // if(uid.type==="Student"){
      // var deviceId=uid.type;
      // console.log(deviceId)
      // }
  });

});


// // database.push({
// //     name:"another"
// // })
// //  exports.helloWorld = functions.https.onRequest((request, response) => {
// //   response.send("Hello from Firebase!");
// //  });

/*  ==============================================================================
 *
 * Node Example: Use express and node.js to create the initial beginnings of 
 * a basic RESTful service
 *
 * ==============================================================================
 */

/* Express provides a framework that makes handling HTTP
 * request/repsonse sequences simpler
 */
// Express provides a framework that makes handling HTTP
// request/repsonse sequences simpler
var express = require('express');
var http = require('http');

// Socket.io allows for network streaming between connected
// clients. It's a convenient way to broadcast data to specific
// clients and also implement real-time services, such as chat
var socketio = require('socket.io');

// Body Parser - body parser is a convenient piece of "middleware"
// that parses the incoming body of the HTTP request before your route
// handler gets access. The nice thing about this is that we are
// sending JSON data and specifying this in the Content-Type portion
// of the HTTP header and because of this, body parser interprets the
// JSON data and places it into named structures in the req.body
// component. Thus, if you have a element named "name" in your JSON,
// you can access its value with req.body.name.
var bodyParser = require('body-parser');

// The main instanced class, called app will be initialized by express
var app = express()

// Much of the Socket.io use in this example is derived from
//   https://socket.io/get-started/chat/
//
// First, we need to get create a HTTP server using the Express app
// reference.
var httpServerRef = http.createServer(app);

// At this point, we can create a reference for listening to network
// communications with socket.io
var networkIORef = socketio.listen(httpServerRef);

// Set the port in the app system
app.set("port", 16081);

// The next two sections tell bodyParser which content types to
// parse. We are mainly interested in JSON, ut eventually, encoded,
// multipart data may be useful.
app.use(bodyParser.urlencoded({   // support encoded bodies
	    extended: true
		}));
app.use(bodyParser.json());  // support json encoded bodies

//Start the app and let it listen for connections
httpServerRef.listen(app.get("port"), function () {
	console.log('Node app listening on port: ', app.get("port"));
    });

//
// This is the main Socket.io handler.  When data is emitted to our
// server, this connection handler will respond with a socket
// connection. The networkIORef is the listening socket. When it
// receives a network connection, a new socket (argument of inline
// function below) will be created to process the network data that
// was emitted from a client.
//
networkIORef.on('connection', function(socket) {
	console.log('user connected');
	
// At this point, the socket connection has been made.  We now
// need to process the type of message that was sent. This is
// somewhat similar to how routes are processed with express.  We
// give a name, in this case, "log message" when we emit data (see
// below in the POST route handler). When a "log message" packet
// comes in, this function processes it but 

// if we get a log message, we blindly emit it to all of the
// connected clients that have made connections to our server.
//
// Note that in this index.js file, no HTTP "connections" are
// made.  To see how we connect a client, check the baseHTML.html
// file that we send back on the Default GET route (below).
// 
	socket.on('log message', function(msg){
		networkIORef.emit('log message', msg);
	    });
	socket.on('disconnect', function(){
		console.log('user now disconnected');
	    });
    });
var reservationList = {
    reservationArray : []
};

var dataToReturn = {
    "name" : "John",
    "phoneNumber" : "320-815-2656",
    "partySize" : "3",
    "request" : "None"
};
    
/* -------------------------------------------------
 * ROUTE Descriptions 
 *
 * This is where you process the GET, POST, PUT, DELETE and other
 * potential routes you might need for your API.
 *
 * Default route -- i.e. the Dashboard for you app...
 */
app.get('/', function(request, response) {
	
	/* This call writes the HTTP Response Header status, which
	 * contains the status code (200 in this case) and the "type" of
	 * the body data (HTML in this case)
	 */
	response.writeHead(200, {'Content-Type': 'text/html'});

	/* Each write statement in the response can send text/data into the body 
	 * of the HTTP Response body */
	response.write('<!DOCTYPE html><head><title>DashBoard</title></head><body>');
	response.write('<H1>Restaurant Paging App</H1>');

	/* You can choose to write other data, such as transforming your internal 
	 * Javascript state into elements you can view on a Dashboard to help you 
	 * debug what's happening. This option may also be a useful tool for your 
	 * clients too since you could tailor the output for an administrative inteface.
	 */
	response.write('JSON Data:');
	response.write(JSON.stringify(dataToReturn));
        /* You could output any JavaScript data here... */

        response.write('</body></html>');

        /* When the body content is completely written, you need to end the write statement
         * which will cause it to be sent on the network. 
         */
        response.end();

        /* Occasional console output can be helpful for debugging too. */
	console.log('Received Dashboard request!');
	MPGData.MPGArray.push(dataToReturn);

    });

/* GET - retrieves data from server */
app.get('/getData', function (request, response) {
	res.sendFile(__dirname + '/baseHTML.html');
    });

/* POST - sends data to server */
app.post('/api/exportData', function (request, response) {

	/* If for some reason, the JSON isn't parsed due to error, return a HTTP ERROR
	 * 400 - in other words, if there is no body
	 */
	if (!request.body) return response.sendStatus(400);

	// Get a timestamp that we can use to seeing ordered log messages
	var timestamp = new Date().valueOf();

	// In this example, I'm just iterating over all of the JSON
	// elements in the REQUEST body to write to a "log" message.
	var logstr = '';
	for(var elemName in req.body) {
	    logstr = logstr + "[" + elemName + ": " + req.body[elemName] + "] ";
	}

	// With the log message data, EMIT a network packet to our
	// socket.io network reference - this will send a packet with the
	// string (the second argument below) to our socket.io
	// connection. See the connection handler above for what happens
	// when the server receives this data.
	networkIORef.emit('log message', timestamp + ': Received /api/exportData POST' + logstr);

	// Finally, send a status RESPONSE back letting the client know
	// the POST succeeded.
	res.status(200).send('OK');
    })

/* PUT - sends data to server */
app.put('/putData', function (request, response) {
	console.log('PUT REQUEST: putData');
    });

/* DELETE - removes data from server */
app.delete('/deleteData', function (request, response) {
	console.log('DELETE REQUEST: Data has been deleted.');
    });


/* ================================================                          
 * ================================================                          
 * ================================================                          
 * 
 * Error handeling section
 *
 * 404- Missing page handler
 *
 * 500- Broken Page Error
 * ================================================                          
 * ================================================                          
 * ================================================                          
 */

app.use(function(request, response, next){
	request.status(404).send('Sorry, cant find that page!');
    });

app.use(function(error, request, response, next){
	console.error(error.stack);
	request.status(500).send('Something broke!');
    });

/* ================================================
 * ================================================
 * ================================================
 * 
 * FINALLY, start the app and let it listen for connections on the
 * network
 *
 * This really needs to be last.
 *
 * app.listen opens up a network socket on port "port" and waits for
 * HTTP connections
 *
 * ================================================
 * ================================================
 * ================================================
 */


app.listen(app.get("port"), function () {
	console.log('Restaurant Paging System: Node app listening on port: ', app.get("port"));
    });
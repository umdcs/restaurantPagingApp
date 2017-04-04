package edu.umn.d.restaurantpagingapp;

import io.socket.emitter.Emitter;
import io.socket.hasbinary.HasBinary;
import io.socket.parser.Packet;
import io.socket.parser.Parser;
import io.socket.thread.EventThread;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.String;


/** Socket Class for Client */

public class extends Emitter {
        private static final Logger logger = Logger.getLogger(Socket.class.getName());

        /** Called on Connection */
        public static final String EVENT_CONNECT = "connect";

        public static final String EVENT_CONNECTING = "connecting";

        /** Called on disconnection */
        public static final String EVENT_DISCONNECT = "disconnect";

        /** Connection errors */

        public static final String EVENT_ERROR = "error";
        public static final String EVENT_MESSAGE = "message";
        }


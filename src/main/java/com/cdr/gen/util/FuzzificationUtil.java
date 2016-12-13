package com.cdr.gen.util;

import org.joda.time.Interval;

import com.cdr.gen.Call;
import com.cdr.gen.FuzzifiedCall;

public class FuzzificationUtil {

    public static FuzzifiedCall FuzzificateCall(Call call) {

	FuzzifiedCall fuzzifiedCall = new FuzzifiedCall();
	fuzzifiedCall.setId(call.getId());
	fuzzifiedCall.setType(call.getType());
	fuzzifiedCall.setDestPhoneNumber(call.getDestPhoneNumber());
	fuzzifiedCall.setCost(convertCost(call.getCost()));
	fuzzifiedCall.setDuration(convertInterval(call.getTime()));
	fuzzifiedCall.setFraud(fraudStatus(call));

	return fuzzifiedCall;

    }

    public static String convertCost(double cost) {

	if (cost < 100) {
	    return "Short";
	}

	else if (cost >= 100 && cost <= 500) {
	    return "Normal";

	}

	else if (cost > 500 && cost <= 1000) {

	    return "High";
	}

	return "Normal";

    }

    public static String convertInterval(Interval time) {

	Long cost = time.toDuration().getStandardMinutes();

	if (cost < 30) {
	    return "Short";
	}

	else if (cost >= 30 && cost <= 60) {
	    return "Normal";

	}

	else if (cost > 60 && cost <= 1000) {

	    return "Long";
	}

	return "Normal";

    }

    public static String fraudStatus(Call call) {

	String returnString = "Legit";

	String cost = convertCost(call.getCost());
	String duration = convertInterval(call.getTime());
	String type = call.getType();

	switch (type) {

	case "Local":

	    if (cost.equalsIgnoreCase("High")) {

		returnString = "Fraud";

	    }

	    else if (duration.equalsIgnoreCase("Long")) {

		returnString = "Fraud";
	    }

	    break;

	case "Intl":

	    if (cost.equalsIgnoreCase("High")) {

		returnString = "Fraud";

	    }

	    else if (duration.equalsIgnoreCase("Long")) {

		returnString = "Fraud";
	    }

	    break;

	case "Mobile":

	    if (cost.equalsIgnoreCase("High") && duration.equalsIgnoreCase("Normal")) {

		returnString = "Suspicious";

	    }

	    else if (cost.equalsIgnoreCase("High") && duration.equalsIgnoreCase("Long")) {

		returnString = "Fraud";
	    }

	    break;

	default:
	    break;
	}

	return returnString;

    }

}

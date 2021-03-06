package com.iri.training.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iri.training.model.Metrics;
import com.iri.training.web.service.MetricsService;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "api/metrics")
public final class MetricsController {

	Logger logger = Logger.getLogger(MetricsController.class);

	@Autowired
	MetricsService metricsService;

	@RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = "application/json")
	public ResponseEntity<String> editMetrics(@RequestBody Metrics metrics) throws SQLException {

		logger.debug("ENTERED editMetrics: " + metrics );
		if ( metrics !=null) {
			metricsService.updateMetrics(metrics);

			logger.debug("EXITING editMetrics: " + metrics);

			return new ResponseEntity("{\"message\": \"Update success.\"}", HttpStatus.OK);
		}else{
			return new ResponseEntity("{\"message\": \"Update failed.\"}", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public Metrics getMetricsByUserId(final HttpServletRequest request,@PathVariable final Long userId) throws SQLException {

		logger.debug("ENTERED getMetricsByUserId");

		Metrics metrics = metricsService.getMetricsByUserId(userId);

		logger.debug("EXITING getMetricsByUserId");

		return metrics;
	}

	@RequestMapping(value ="/list", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Metrics>> getAllMetrics() throws SQLException {

		logger.debug("ENTERED getAllMetrics");

		ArrayList<Metrics> metrics = (ArrayList) metricsService.getMetricsList();
		if (metrics != null) {
			return new ResponseEntity<ArrayList<Metrics>>(metrics, HttpStatus.OK);
		}

		logger.debug("EXITING getAllMetrics");

		return new ResponseEntity<ArrayList<Metrics>>(HttpStatus.NOT_FOUND);
	}
}

package com.arj.covidtracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.arj.covidtracker.model.LocationStats;


@Service
public class CoronaDataService {

	private String dataUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
	private String stateWise = "https://www.mohfw.gov.in/pdf/DistrictWiseList354.pdf";
	private List<LocationStats> allStats = new ArrayList();
	
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}


	@PostConstruct
	@Scheduled(cron ="* * 1 * * *")
	public void getVirusData() throws IOException, InterruptedException {
	
		List<LocationStats> newAllStats = new ArrayList();	// to avoid concurrency as lots of people are acccessing this data
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request =  HttpRequest.newBuilder().
				uri(URI.create(dataUrl)).build();
		//HttpRequest requestStateWise =  HttpRequest.newBuilder().
			//	uri(URI.create(stateWise)).build();
		
		HttpResponse<String> httpResponse =  client.send(request, HttpResponse.BodyHandlers.ofString());
		
		//HttpResponse<String> httpResponseStates =  client.send(requestStateWise, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpResponseStates.body());
		
		
		//System.out.println(httpResponse.body());
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		//Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
		    
			
			String country = record.get("Country/Region");
		  //  System.out.println(country);
		  
		  
			
		    //String customerNo = record.get("CustomerNo");
		    //String name = record.get("Name");
		    
		    LocationStats locStat = new LocationStats();
		    locStat.setCountry(country);
		    locStat.setLatestTotalDeaths(Integer.parseInt(record.get(record.size() -1)));
		    
		    
		    System.out.println(locStat);
		    newAllStats.add(locStat);
		}
		
		this.allStats = newAllStats;
	}
	
}

package com.nnacres.assessment.service.impl;

import com.SphereEngine.Api.Exception.ClientException;
import com.SphereEngine.Api.Exception.ConnectionException;
import com.SphereEngine.Api.Exception.NotAuthorizedException;
import com.SphereEngine.Api.ProblemsClientV3;
import com.nnacres.assessment.service.SphereEngineProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@Slf4j
public class SphereEngineProblemServiceImpl implements SphereEngineProblemService {

    private ProblemsClientV3 client;


    @PostConstruct
    public void setProblemsClientV3() {
        client = new ProblemsClientV3("10d19c386011cca0062a0a65fb096bf4",
            "43731bfd.problems.sphere-engine.com");
        try {
            client.test();
        } catch (NotAuthorizedException e) {
            log.error("Invalid access token");
        } catch (ConnectionException e) {
            log.error("API connection problem");
        } catch (ClientException e) {
            log.error("Client Exception problem");
        }
    }
}

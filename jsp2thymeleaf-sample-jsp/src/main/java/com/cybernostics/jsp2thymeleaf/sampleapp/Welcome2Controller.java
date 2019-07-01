/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cybernostics.jsp2thymeleaf.sampleapp;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class Welcome2Controller
{

    @Value("${application.message:Hey JSP2Thymeleaf}")
    private String message = "JSP2Thymeleaf";

    @RequestMapping("/welcome2")
    public String welcome(Map<String, Object> model)
    {
        Country[] countryList =
        {
            country(1, "Australia"),
            country(2, "New Zealanad"),
            country(3, "France")
        };

        UserCommand userCommand = new UserCommand();
        userCommand.name = "bob";
        userCommand.gender = Gender.Male;
        userCommand.country = countryList[1];
        userCommand.aboutYou = "no details";
        //        userCommand.communities;
        userCommand.mailingList = false;
        String[] communityList =
        {
            "community1",
            "community2",
            "community3"
        };

        String[] tags =
        {
            "cout", "cchoose", "curl"
        };
        model.put("tags", tags);
        model.put("time", new Date());
        model.put("message", this.message);
        model.put("countryList", countryList);
        model.put("communityList", communityList);
        model.put("user", userCommand);
        return "welcome2";
    }

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    MyRestResponse handleMyRuntimeException(MyException exception)
    {
        return new MyRestResponse("Some data I want to send back to the client.");
    }

    public static class UserCommand
    {

        String name;
        String password;
        String confirmPassword;
        Gender gender;
        Country country;
        String aboutYou;
        String[] communities;
        boolean mailingList;

        public String getName()
        {
            return name;
        }

        public String getPassword()
        {
            return password;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public Gender getGender()
        {
            return gender;
        }

        public Country getCountry()
        {
            return country;
        }

        public String getAboutYou()
        {
            return aboutYou;
        }

        public String[] getCommunities()
        {
            return communities;
        }

        public boolean isMailingList()
        {
            return mailingList;
        }

    }

    public static class Country
    {

        int countryId;
        String countryName;

        private Country(int countryId, String countryName)
        {
            this.countryId = countryId;
            this.countryName = countryName;
        }

        public int getCountryId()
        {
            return countryId;
        }

        public String getCountryName()
        {
            return countryName;
        }

    }

    public static Country country(int countryId, String countryName)
    {
        return new Country(countryId, countryName);
    }

    private static enum Gender
    {

        Male,
        Female
    }
}

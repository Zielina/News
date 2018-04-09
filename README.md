# README #

This README would normally document whatever steps are necessary to get your application up and running.
The project consists of a part of the sdk library and a server part based on Rest Api

To start Please write your Api Key to properties file confi.properties

There are two types of sending request:
- filter by parameters like Country, Category,Sources etc.
- fileter by words parameter KeyWord.

* Folder lib 
   * class RequestParametrization Build object whichc consist parameters to send 
   
   example : new RequestParametrization.RequestParamBuilder().addCategory("technology").addCoutry("us").addPageSize(100).build()
   
   * class CacheDispatcherRequest -> keep in cache respond from https://newsapi.org
   
   * class RequestFactory getNewsObject methode getNewsObject(One of Url from Request Facoty, RequestParametrization)
   
   Build url to NewsApi on the base of RequestParametrization
   
   * class OutputDataFront -> data for filter Country and Category
   
   * class TemplateRequest parent class to send NewsApi Request 

###Dependecy of Lib ###
```
  <dependency>
       <groupId>news.lib</groupId>
       <artifactId>Lib</artifactId>
       version>1.0</version>
   </dependency>
```
### Description about Rest Controller ###

* Rest endpoint GET /news/{lang}/{category}/
*  lang -> The 2-letter ISO 3166-1 code of the country or name of country 
without distinction between uppercase and lowercase letters like Polska,polska,Lotwa,lotwa
*  category -> one for [business entertainment general health science sports technology]

Example for United States of America 
GET/news/us/general or GET/news/unitedstatesofamerica/general
```
{
    "country": "us",
    "category": "general",
    "articles": [
        {
            "author": "CBS/AP",
            "title": "Trump threatens \"Animal Assad,\" Putin over alleged chemical attack in Syria",
            "description": "\"Another humanitarian disaster for no reason whatsoever. SICK!\" the president tweeted on Sunday",
            "date": "2018-04-08T14:15:45Z",
            "sourceName": "CBS News",
            "articleUrl": "https://www.cbsnews.com/news/syria-chemical-attack-trump-threatens-bashar-animal-assad-putin-chemical-weapons-strike-2018-04-08/",
            "imageUrl": "https://cbsnews3.cbsistatic.com/hub/i/r/2018/04/03/f8fb5865-11fa-42b2-8e5c-9c7cb86a3957/thumbnail/1200x630g7/4b40d129142ed6c825b129b26815ce3b/trump-gettyimages-941489298.jpg"
        },
        {
            "author": "author",
            "title": "On 'Saturday Night Live,' Cardi B Debuts Twice",
            "description": "One day after the release of her major label debut, the Bronx rapper made her SNL debut � and used the occasion to introduce a new addition to the Bardi gang.",
            "date": "2018-04-08T14:04:12Z",
            "sourceName": "Npr.org",
            "articleUrl": "https://www.npr.org/sections/allsongs/2018/04/08/600632581/on-saturday-night-live-cardi-b-debuts-twice",
            "imageUrl": "https://media.npr.org/assets/img/2018/04/08/gettyimages-858473738_wide-fe6bcf1928324efc124d62f1d1e64253e2cbbaa4.jpg?s=1400"
        },
		(...)
		]
}
```
* Respond for not correct country 
Example GET /news/an/general
```

{
    "cause": null,
    "stackTrace": [],
    "localizedMessage": "News [ Country = an ] Not Found",
    "message": "News [ Country = an ] Not Found",
    "suppressed": []
}
```
* Respond for not correct category
```
{
    "cause": null,
    "stackTrace": [],
    "localizedMessage": "News [ Category = us ] Not Found",
    "message": "News [ Category = us ] Not Found",
    "suppressed": []
}
```

* Rest Endpoint /news/search?q={value}
*  value - word to search articles
Example /news/search?q=Poland
```
{
    "country": "",
    "category": "",
    "articles": [
        {
            "id": null,
            "author": "https://facebook.com/9gag",
            "title": "Poland？Poland.",
            "description": "Tags: Vladimir Putin 3121 points, 102 comments.",
            "date": "2018-03-09T13:50:01Z",
            "sourceName": "9gag.com",
            "articleUrl": "https://9gag.com/gag/aZ33jvW/polandpoland",
            "imageUrl": "https://images-cdn.9gag.com/photo/aZ33jvW_700b.jpg"
        },
        {
            "id": null,
            "author": null,
            "title": "Poland",
            "description": "I MIGHT have forgotten to uplaod yesterday...My memory is shitty and I'm too lazy to check Anyway, human design for Polandball&nbsp;",
            "date": "2018-03-10T22:35:03Z",
            "sourceName": "Deviantart.com",
            "articleUrl": "https://metr0poiitan.deviantart.com/art/Poland-733956009",
            "imageUrl": "https://orig00.deviantart.net/e5ea/f/2018/063/3/d/poland_by_metr0poiitan-dc4z82x.png"
        },
        (...)
        ]
     }
```
### Controller MVC
Class Controller MVC is example class that use Rest Api. To start please write address of
Rest Url in file config.properties. This class use 
- Java 8
- Thymeleaf
* endpoint=http://address.rest.com
 ##Description Endpoints
 * without pagination
 
 GET/ 
 respond ModelAndView 
 - keyCountries -> List of Countries
 - keyCategories -> List of Categories
 
 POST /  
 countries =  name of country or code
 categories =  name of category
 
 Get / search?q=ABC
 q -> ket words to searching articles
 
 * container html to display respond of articles without pagination
 ```
 <div class="container">
                 <div th:if="${not #lists.isEmpty(keyVisionDatasets)}">
                     <div class="row">
                         <div class="col-md-4" th:each="data : ${keyVisionDatasets.getArticles()}">
                             <h2 th:text="${data.getTitle()}"/>
                             <h3 th:text="@{'Author : '+ ${data.getAuthor()}}"></h3>
                             <p th:text="${data.getDescription()}"/>
                             <p th:text="${data.getDate()}"/>
                             <p th:text="@{'Source Name : ' +${data.getSourceName()}}"/>
                             <a th:href="${data.getArticleUrl()}"/>
                             <img th:src="${data.getImageUrl()}" width="50" height="50"/>
                         </div>
                     </div>
                 </div>
             </div>
 ```
###Pagination
 Get /searchNews?q=ABC 
 * q = abc,
 * pageNumber = 1
 * redirection to page "/searchNews/{q}/{pageNumber}"
  
  Get /searchNews/{q}/{pageNumber}
  * q -> key wards
  *pageNumber -> number of Page
  
   * container html to display respond of articles with pagination

  ```
  <div th:if="${keyNews}">
                  <div class="row">
                      <div class="col-md-4" th:each="data : ${keyNews}">
                          <h2 th:text="${data.title}"/>
                          <h3 th:text="@{'Author : '+ ${data.author}}"></h3>
                          <p th:text="${data.description}"/>
                          <p th:text="${data.date}"/>
                          <p th:text="@{'Source Name : ' +${data.sourceName}}"/>
                          <a th:href="${data.articleUrl}"/>
                          <img th:src="${data.imageUrl}" width="50" height="50"/>
                      </div>
                  </div>
  </div>
  ```
  
  * Pagination
  
  ```
   <div class="row">
                      <nav aria-label="Page navigation example">
                          <ul class="pagination">
                              <li class="page-item" th:class="${currentIndex == 1}? 'disabled' : ''">
                                  <span class="page-link" th:if='${currentIndex == 1}'>First</span>
                                  <a class="page-link" th:if='${currentIndex != 1}'
                                     th:href="${baseUrl}+'1'">First</a>
                              </li>
                              <li class="page-item" th:class="${currentIndex != 1}? '' : 'disabled'">
                                  <span class="page-link" th:if='${currentIndex == 1}'>«</span>
                                  <a class="page-link" th:if='${currentIndex != 1}'
                                     th:href="@{|${baseUrl}$(currentIndex - 1)|}">«</a>
                              </li>
                              <li class="page-item" th:each="item : ${#numbers.sequence(beginIndex,endIndex)}"
                                  th:class="${item == currentIndex ? 'active' : '' }">
                                  <span class="page-link" th:if='${item == currentIndex}' th:text='${item}'/>
                                  <a class="page-link" th:if='${item != currentIndex}'
                                     th:href="@{|${baseUrl}${item }|}">
                                      <span th:text='${item}'/>
                                  </a>
                              </li>
                              <li class="page-item" th:class="${currentIndex != totalPageCount}? '' : 'disabled'">
                                  <span class="page-link" th:if='${currentIndex == totalPageCount}'>»</span>
                                  <a class="page-link" th:if='${currentIndex != totalPageCount}'
                                     th:href="@{|${baseUrl}${currentIndex + 1}|}"
                                  >»</a>
                              </li>
                              <li class="page-item" th:class="${currentIndex == totalPageCount}? 'disabled' : ''">
                                  <span class="page-link" th:if='${currentIndex == totalPageCount}'>Last</span>
                                  <a class="page-link" th:if='${currentIndex != totalPageCount}'
                                     th:href="@{|${baseUrl}${totalPageCount}|}">Last</a>
                              </li>
                          </ul>
                      </nav>
                  </div>
              </div>
```
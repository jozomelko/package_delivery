**Build instructions (mac/linux)**

* `./gradlew clean build`

**Run application**

* `./gradlew run --args='-p <file with package informations> -f <file with fee informations>'`
  
* application could be also run without arguments or only with one of them

**Restrictions and rules**

* `correct input for package informations: <weight: positive number, >0, maximal 3 decimal places, . (dot) as decimal separator><space><postal code: fixed 5 digits>`
* `correct input for fee information: <weight: positive number, >0, maximal 3 decimal places, . (dot) as decimal separator><space><fee: positive number, >=0, fixed two decimals, . (dot) as decimal separator>`
* `fees can be loaded only via file`
* `after application run, user is not able to load file. It is possible only at the beginning as argument.`
* `input has only two attributes separated with space. Everything behind second attribute will be ignored.`

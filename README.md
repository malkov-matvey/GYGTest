# Best Berlin tour reviews
Get Your Guide test project with reviews of the most popular tour in Berlin. Developed by Matvei Malkov, 27 July 2018

### Libs used:

* design support / app compat — for Rating bar, floating buttons and just to be material friendly
* arch components — room and viewmodels are used in this project
* retrofit — to make REST queries to backend
* rxjava2 — to develop neat ViewModels and make room and retrofit queries more stable, understandable and unified
* rxandroid — normally I don't need it, for now I just use `AndroidSchedulers.mainThread()` to speed up developemnt time
* gson — not necessary in this small project too, just to speed up developement
* misc — retrofit rxjava2 / gson adapters, recyclerview

### How to build: 
`./gradlew build`

### API for sumbittin review: 
I decided to use the same retrofit `RestAdapter` with POST request and json `@Body` inside. Please, see [GYGReviewsService.java](https://github.com/malkov-matvey/GYGTest/blob/222f40f5c2422b003680d92a0d820c983b773ce4/app/src/main/java/malkov/name/gygtest/network/GYGReviewsService.java#L22) for detailed info.

### Tests:
There's no tests ¯\\\_(ツ)\_/¯

### Futher changes:
There're quite a lot of things can make this project even better. For example, I can add `BroadcastReceiver` for `NETWORK_CHANGED` event. However, there's no limit for perfection and this version is production-ready like a Minimal Valuable Product.

import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        bottom: false,
        child: Container(
          color: Colors.black,
          child: Column(
            // -- 주측 정렬
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Container(
                height: 50.0,
                color: Colors.black,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Container(
                      width: 50.0,
                      color: Colors.red,
                    ),
                    Container(
                      width: 50.0,
                      color: Colors.orange,
                    ),
                    Container(
                      width: 50.0,
                      color: Colors.yellow,
                    ),
                    Container(
                      width: 50.0,
                      color: Colors.green,
                    )
                  ],
                ),
              ),
              Container(
                height: 50.0,
                color: Colors.black,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      width: 50.0,
                      color: Colors.orange,
                    )
                  ],
                ),
              ),
              Container(
                height: 50.0,
                color: Colors.black,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Container(
                      width: 50.0,
                      color: Colors.red,
                    ),
                    Container(
                      width: 50.0,
                      color: Colors.orange,
                    ),
                    Container(
                      width: 50.0,
                      color: Colors.yellow,
                    ),
                    Container(
                      width: 50.0,
                      color: Colors.green,
                    )
                  ],
                ),
              ),
              Container(
                height: 50.0,
                color: Colors.black,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      width: 50.0,
                      color: Colors.green,
                    )
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

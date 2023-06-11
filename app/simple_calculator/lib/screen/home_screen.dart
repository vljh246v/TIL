import 'package:flutter/material.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int num1 = 0;
  int num2 = 0;
  int displayNum = 0;
  int result = 0;
  String op = '';
  bool inputOp = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              flex: 1,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(displayNum.toString()),
                ],
              ),
            ),
            Expanded(
              flex: 2,
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      children: [
                        Expanded(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              ElevatedButton(
                                onPressed: () {
                                  setState(() {
                                    if(!inputOp) {
                                      if(num1 == 0) {
                                        num1 = int.parse('1');
                                      } else {
                                        num1 = int.parse('${num1}1');
                                      }
                                      displayNum = num1;
                                    } else {
                                      if(num2 == 0) {
                                        num2 = int.parse('1');
                                      } else {
                                        num2 = int.parse('${num2}1');
                                      }
                                      displayNum = num2;
                                    }
                                  });
                                },
                                child: Text('1'),
                              ),
                            ],
                          ),
                        ),
                        Expanded(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              ElevatedButton(
                                onPressed: () {
                                  setState(() {
                                    if(!inputOp) {
                                      if(num1 == 0) {
                                        num1 = int.parse('4');
                                      } else {
                                        num1 = int.parse('${num1}4');
                                      }
                                      displayNum = num1;
                                    } else {
                                      if(num2 == 0) {
                                        num2 = int.parse('4');
                                      } else {
                                        num2 = int.parse('${num2}4');
                                      }
                                      displayNum = num2;
                                    }
                                  });
                                },
                                child: Text('4'),
                              ),
                            ],
                          ),
                        ),
                        Expanded(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              ElevatedButton(
                                onPressed: () {
                                  setState(() {
                                    if(!inputOp) {
                                      if(num1 == 0) {
                                        num1 = int.parse('7');
                                      } else {
                                        num1 = int.parse('${num1}7');
                                      }
                                      displayNum = num1;
                                    } else {
                                      if(num2 == 0) {
                                        num2 = int.parse('7');
                                      } else {
                                        num2 = int.parse('${num2}7');
                                      }
                                      displayNum = num2;
                                    }
                                  });
                                },
                                child: Text('7'),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Column(
                        children: [
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      if(!inputOp) {
                                        if(num1 == 0) {
                                          num1 = int.parse('2');
                                        } else {
                                          num1 = int.parse('${num1}2');
                                        }
                                        displayNum = num1;
                                      } else {
                                        if(num2 == 0) {
                                          num2 = int.parse('2');
                                        } else {
                                          num2 = int.parse('${num2}2');
                                        }
                                        displayNum = num2;
                                      }
                                    });
                                  },
                                  child: Text('2'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      if(!inputOp) {
                                        if(num1 == 0) {
                                          num1 = int.parse('5');
                                        } else {
                                          num1 = int.parse('${num1}5');
                                        }
                                        displayNum = num1;
                                      } else {
                                        if(num2 == 0) {
                                          num2 = int.parse('5');
                                        } else {
                                          num2 = int.parse('${num2}5');
                                        }
                                        displayNum = num2;
                                      }
                                    });
                                  },
                                  child: Text('5'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      if(!inputOp) {
                                        if(num1 == 0) {
                                          num1 = int.parse('8');
                                        } else {
                                          num1 = int.parse('${num1}8');
                                        }
                                        displayNum = num1;
                                      } else {
                                        if(num2 == 0) {
                                          num2 = int.parse('8');
                                        } else {
                                          num2 = int.parse('${num2}8');
                                        }
                                        displayNum = num2;
                                      }
                                    });
                                  },
                                  child: Text('8'),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Column(
                        children: [
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      if(!inputOp) {
                                        if(num1 == 0) {
                                          num1 = int.parse('3');
                                        } else {
                                          num1 = int.parse('${num1}3');
                                        }
                                        displayNum = num1;
                                      } else {
                                        if(num2 == 0) {
                                          num2 = int.parse('3');
                                        } else {
                                          num2 = int.parse('${num2}3');
                                        }
                                        displayNum = num2;
                                      }
                                    });
                                  },
                                  child: Text('3'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      if(!inputOp) {
                                        if(num1 == 0) {
                                          num1 = int.parse('6');
                                        } else {
                                          num1 = int.parse('${num1}6');
                                        }
                                        displayNum = num1;
                                      } else {
                                        if(num2 == 0) {
                                          num2 = int.parse('6');
                                        } else {
                                          num2 = int.parse('${num2}6');
                                        }
                                        displayNum = num2;
                                      }
                                    });
                                  },
                                  child: Text('6'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      if(!inputOp) {
                                        if(num1 == 0) {
                                          num1 = int.parse('9');
                                        } else {
                                          num1 = int.parse('${num1}9');
                                        }
                                        displayNum = num1;
                                      } else {
                                        if(num2 == 0) {
                                          num2 = int.parse('9');
                                        } else {
                                          num2 = int.parse('${num2}9');
                                        }
                                        displayNum = num2;
                                      }
                                    });
                                  },
                                  child: Text('9'),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Column(
                        children: [
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      inputOp = true;
                                      op = '+';
                                    });
                                  },
                                  child: Text('+'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      inputOp = true;
                                      op = '-';
                                    });
                                  },
                                  child: Text('-'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      inputOp = true;
                                      op = '*';
                                    });
                                  },
                                  child: Text('*'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      inputOp = true;
                                      op = '/';
                                    });
                                  },
                                  child: Text('/'),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    if(inputOp) {
                                      setState(() {
                                        if(op == '+') {
                                          displayNum = num1 + num2;
                                        } else if(op == '-') {
                                          displayNum = num1 - num2;
                                        } else if(op == '*') {
                                          displayNum = num1 * num2;
                                        } else if(op == '/') {
                                          displayNum = (num1 / num2).toInt();
                                        }

                                        num1 = 0;
                                        num2 = 0;
                                        inputOp = false;
                                        op = '';
                                      });
                                    }
                                  },
                                  child: Text('='),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      num1 = 0;
                                      num2 = 0;
                                      displayNum = 0;
                                      inputOp = false;
                                      op = '';
                                    });
                                  },
                                  child: Text('C'),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

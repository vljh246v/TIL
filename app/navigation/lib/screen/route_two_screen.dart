import 'package:flutter/material.dart';
import 'package:navigation/screen/route_three_screen.dart';

import '../layout/main_layout.dart';

class RouteTwoScreen extends StatelessWidget {
  const RouteTwoScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final argument = ModalRoute.of(context)!.settings.arguments;
    return MainLayout(
      title: 'Route Two',
      children: [
        Text(
          'arguments : $argument',
          textAlign: TextAlign.center,
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          child: Text('Pop'),
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.of(context).pushNamed(
              '/three',
              arguments: 999,
            );
          },
          child: Text('Push Named'),
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                builder: (_) => RouteThreeScreen(),
                settings: RouteSettings(
                  arguments: 998
                ),
              ),
            );
          },
          child: Text('Push Replacement'),
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.of(context)
                .pushReplacementNamed('/three', arguments: 997);
          },
          child: Text('Push ReplacementNamed'),
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.of(context).pushAndRemoveUntil(
              MaterialPageRoute(
                  builder: (_) => RouteThreeScreen(),
                  settings: RouteSettings(
                    arguments: 996,
                  )),
              (route) => route.settings.name == '/',
            );
          },
          child: Text('Push And RemoveUntil'),
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.of(context).pushNamedAndRemoveUntil(
              '/three',
              (route) => route.settings.name == '/',
              arguments: 995,
            );
          },
          child: Text('Push Named And RemoveUntil'),
        ),
      ],
    );
  }
}

import 'package:flutter/material.dart';

class NumberRow extends StatelessWidget {
  final int number;

  const NumberRow({
    required this.number,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      children: number
          .toString()
          .split('')
          .map((e) => Image.asset(
                'asset/img/$e.png',
                height: 70.0,
                width: 50.0,
              ))
          .toList(),
    );
  }
}

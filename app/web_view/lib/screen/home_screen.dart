import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

final homeUrl = Uri.parse('https://blog.codefactory.ai');

class HomeScreen extends StatelessWidget {
  WebViewController controller = WebViewController()
  ..setJavaScriptMode(JavaScriptMode.unrestricted)
  ..loadRequest(homeUrl);

  HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          centerTitle: true,
          backgroundColor: Colors.orange,
          title: Text('Demoversion'),
          actions: [
            IconButton(
                onPressed: () {
                  controller.loadRequest(homeUrl);
                },
                icon: Icon(Icons.home))
          ],
        ),
        body:WebViewWidget(
          controller: controller,
        )
    );
  }
}

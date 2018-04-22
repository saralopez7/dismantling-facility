using System;
using System.IO;
using System.Net;
using System.Text;
using System.Windows;
using Caliburn.Micro;
using Newtonsoft.Json;

namespace DismantleStationClient.ViewModels
{
    public class ClientViewModel : Screen
    {
        private Uri _uri;
        private string _requestMethod;
        private string _selectedRequestMethod;
        private string _jsonResponse;
        private string _body;

        public ClientViewModel()
        {
            RequestMethods.Add(WebRequestMethods.Http.Get);
            RequestMethods.Add(WebRequestMethods.Http.Post);
            RequestMethods.Add(WebRequestMethods.Http.Put);
        }

        public string RequestMethod
        {
            get => _requestMethod;
            set
            {
                _requestMethod = value;
                NotifyOfPropertyChange(() => RequestMethod);
            }
        }

        public Uri Uri
        {
            get => _uri;
            set
            {
                _uri = value;
                NotifyOfPropertyChange(() => Uri);
            }
        }

        public BindableCollection<string> RequestMethods { get; set; } = new BindableCollection<string>();

        public string SelectedRequestMethod
        {
            get => _selectedRequestMethod;
            set
            {
                _selectedRequestMethod = value;
                NotifyOfPropertyChange(() => SelectedRequestMethod);
            }
        }

        public string JsonResponse
        {
            get => _jsonResponse;
            set
            {
                _jsonResponse = value;
                NotifyOfPropertyChange(() => JsonResponse);
            }
        }

        public string Body
        {
            get => _body;
            set
            {
                _body = value;
                NotifyOfPropertyChange(() => Body);
            }
        }

        public void Send(Uri uri, string requestMethods)
        {
            try
            {
                WebRequest request = WebRequest.Create(Uri);
                request.Method = SelectedRequestMethod;

                if (SelectedRequestMethod.Equals(WebRequestMethods.Http.Post) ||
                    SelectedRequestMethod.Equals(WebRequestMethods.Http.Put))
                {
                    request.ContentType = "application/json";
                    byte[] data = Encoding.ASCII.GetBytes(Body);
                    request.ContentLength = data.Length;

                    Stream postStream = request.GetRequestStream();

                    postStream.Write(data, 0, data.Length);

                    postStream.Close();
                }

                // Get response
                WebResponse webResponse = request.GetResponse();

                // Get the stream containing the content returned by the server.
                Stream dataStream = webResponse.GetResponseStream();

                if (dataStream == null)
                {
                    return;
                }

                // Open the stream
                var reader = new StreamReader(dataStream);

                string responseFromServer = reader.ReadToEnd();

                var jsonResponse = JsonConvert.DeserializeObject(responseFromServer);
                JsonResponse = jsonResponse.ToString();
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message, "Error", MessageBoxButton.OK,
                    MessageBoxImage.Error);
            }
        }

        public bool CanSend(Uri uri, string requestMethods)
        {
            return !string.IsNullOrEmpty(uri.ToString()) && !string.IsNullOrEmpty(requestMethods);
        }
    }
}
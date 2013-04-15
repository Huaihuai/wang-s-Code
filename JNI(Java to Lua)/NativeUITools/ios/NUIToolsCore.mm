//
//  NUIToolsCore.m
//  pinkture
//
//  Created by alex chen on 12-9-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "NUIToolsCore.h"
#import "NUITools.h"

@implementation NUIToolsCore

-(id)init
{
    self = [super init];
    if (self) {
        m_returnText1 = @"";
        m_returnBtn1 = -1;
        m_ActionSheetRelBtn = -1;
        
        progressHub = nil;
        progressHubComplete = nil;
        
        m_alertDialogShowing = false;
    }
    
    return self;
}

- (void)dealloc
{
    [super dealloc];
}

- (void)ShowInputDialogWithTitle:(NSString*)tTitle withMsg:(NSString*)tMessage withContnet:(NSString*)tContent withPlaceholder:(NSString*)tHolder withButtons:(NSString*)tButtons
{
    if(tMessage != NULL)
    {
        tMessage = NSLocalizedString(tMessage,nil);
        tMessage =  [tMessage stringByAppendingString:@"\n\n"];
    }
    else {
        tMessage = @"\n\n";
    }
    
    NSArray* nastr = [tButtons componentsSeparatedByString:@"|"];
    
    UIAlertView* alertView = [[UIAlertView alloc]initWithTitle:NSLocalizedString(tTitle,nil) 
                                                       message:tMessage
                                                      delegate:self
                                             cancelButtonTitle:NSLocalizedString([nastr objectAtIndex:0],nil)
                                             otherButtonTitles:nil];
    for (int i=1; i < [nastr count]; i++) {
        [alertView addButtonWithTitle:NSLocalizedString([nastr objectAtIndex:i],nil)];
    }
    
    UITextField *textField = [[UITextField alloc] initWithFrame:CGRectMake(14.0, 70.0, 255.0, 25.0)];
    [textField setBackgroundColor:[UIColor whiteColor]];
    [textField setPlaceholder:NSLocalizedString(tHolder,nil)];
    textField.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
    textField.borderStyle = UITextBorderStyleRoundedRect;
    textField.tag = 111;
    textField.text = NSLocalizedString(tContent,nil);
    [textField becomeFirstResponder];
    [alertView addSubview:textField];
    alertView.tag = 111;    //set input tag for identity this dialog
    
    //[alertView setTransform:CGAffineTransformMakeTranslation(0.0, -100.0)];
    
    //[alertView setBounds:CGRectMake(27.0, 60.0, 230.0, 250.0)];
    
    [textField release];
    [alertView show];
    [alertView release];
}


- (void)ShowAlertDialogWithTitle:(NSString*)tTitle withMsg:(NSString*)tMessage  withButtons:(NSString*)tButtons
{
    if( m_alertDialogShowing == true)
    {
        return;
    }
    
    NSArray* nastr = [tButtons componentsSeparatedByString:@"|"];
    
    UIAlertView* alertView = [[UIAlertView alloc]initWithTitle:NSLocalizedString(tTitle,nil) 
                                                       message:NSLocalizedString(tMessage,nil) 
                                                      delegate:self
                                             cancelButtonTitle:NSLocalizedString([nastr objectAtIndex:0],nil)
                                             otherButtonTitles:nil];
    for (int i=1; i < [nastr count]; i++) {
        [alertView addButtonWithTitle:NSLocalizedString([nastr objectAtIndex:i],nil)];
    }
    
    alertView.tag = 222;    //set alert tag for identity this dialog
    [alertView show];
    [alertView release];
    
    m_alertDialogShowing = true;
}

-(void)ShowIndicatorWithType:(int)ttype withInfo:(NSString*)tinfo
{
    if (progressHub != nil) {
        //printf("ShowIndicator error\n");
        return;
    }
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (!window)
    {
        window = [[UIApplication sharedApplication].windows objectAtIndex:0];
    }
    progressHub = [[MBProgressHUD alloc] initWithWindow:window ];
    
    if (ttype == 1 || ttype == 2) {
        [window addSubview:progressHub];
    }
    else{
            [window.rootViewController.view addSubview:progressHub];
    }
    
    if (ttype == 1 || ttype == 3) {
        progressHub.dimBackground = YES;
    }
    else {
        progressHub.dimBackground = NO;
    }
    
    progressHub.labelText = NSLocalizedString(tinfo, nil);
    [progressHub show:YES];

}

-(void)ShowIndicatorCompleteWith:(BOOL)tAnimated delay:(double)tDelay into:(NSString*)tInfo
{
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (!window)
    {
        window = [[UIApplication sharedApplication].windows objectAtIndex:0];
    }
    progressHubComplete = [[MBProgressHUD alloc] initWithWindow:window ];
    [window addSubview:progressHubComplete];
    
    progressHubComplete.customView = [[[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]] autorelease];
	
	// Set custom view mode
	progressHubComplete.mode = MBProgressHUDModeCustomView;
	
	//progressHubComplete.delegate = self;
	progressHubComplete.labelText = tInfo;
	
	[progressHubComplete show:tAnimated];
	[progressHubComplete hide:tAnimated afterDelay:tDelay];
}

-(void)HideIndicator
{
    if (progressHub != nil) {
        [progressHub hide:YES];
        [progressHub removeFromSuperview];
        [progressHub release];
        progressHub = nil;
    }
}



-(void)ShowActionSheet:(NSString*)tTitle withCancelBtn:(NSString*)tCancelBtn withDestructiveBtn:(NSString*)tDesBtn withButtons:(NSString*)tButtons
{
    NSArray* nastr = [tButtons componentsSeparatedByString:@"|"];
    UIActionSheet *actionSheet = [[UIActionSheet alloc]
                                  initWithTitle:NSLocalizedString(tTitle,nil)
                                  delegate:self
                                  cancelButtonTitle:nil//NSLocalizedString(tCancelBtn,nil)
                                  //destructiveButtonTitle:NSLocalizedString(tDesBtn,nil)
                                  destructiveButtonTitle:nil
                                  otherButtonTitles:nil];
    int btncount = 0;
    if ( ![tDesBtn isEqualToString:@""]) {
        [actionSheet addButtonWithTitle:NSLocalizedString(tDesBtn,nil)];
        actionSheet.destructiveButtonIndex = btncount;
        btncount++;
    }
    
    for (int i=0; i < [nastr count]; i++) {
        [actionSheet addButtonWithTitle:NSLocalizedString([nastr objectAtIndex:i],nil)];
        btncount++;
    }
    
    if ( ![tCancelBtn isEqualToString:@""]) {
        [actionSheet addButtonWithTitle:NSLocalizedString(tCancelBtn,nil)];
        actionSheet.cancelButtonIndex = btncount;
        btncount++;
    }
    //actionSheet.actionSheetStyle = UIActionSheetStyleBlackOpaque;
    
    //show sheet
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (!window)
    {
        window = [[UIApplication sharedApplication].windows objectAtIndex:0];
    }
    [actionSheet showInView:window];
    [actionSheet release];
}

-(int)GetActionSheetBtnResult
{
    return m_ActionSheetRelBtn;
}

#pragma -
#pragma mark AlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
}
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    m_alertDialogShowing = false;
    if ( alertView.tag == 111) {
        //if ( buttonIndex == 1) {
        NSArray* views = [alertView subviews];
        for (UIView *view in views) {
            if ( view.tag == 111 ) {
                UITextField* textview = (UITextField*)view;
                m_returnText1 = textview.text;
                break;
            }
        }
        //}
        m_returnBtn1 = buttonIndex;
        NUITools::NUITools_CompletetCallback();
    }
    else if ( alertView.tag == 222 ) {
        m_returnBtn1 = buttonIndex;
        NUITools::NUITools_CompletetCallback();
    }
}
- (void)willPresentAlertView:(UIAlertView *)alertView
{
//    [alertView setFrame:CGRectMake( alertView.frame.origin.x,  alertView.frame.origin.y, alertView.frame.size.width, alertView.frame.size.height+100)];
//    [alertView setBounds:CGRectMake( alertView.bounds.origin.x,  alertView.bounds.origin.y, alertView.bounds.size.width, alertView.bounds.size.height+100)];
}

- (NSString*)GetInputDialogResult
{
    return m_returnText1;
}
- (int)GetAlertDialogBtnResult
{
    return m_returnBtn1;
}

#pragma -
#pragma mark ShowAppStoreInfo
-(BOOL)ShowAppStoreInfo:(NSString*)tAppId
{
    float version = [[[UIDevice currentDevice] systemVersion] floatValue];
    if ( version < 6.0 ) {
        return false;
    }
    SKStoreProductViewController *controller = [[SKStoreProductViewController alloc] init];
    controller.delegate = self;
    NSDictionary *params = [NSDictionary dictionaryWithObject:tAppId // App id
                                                       forKey:SKStoreProductParameterITunesItemIdentifier];
    
    [controller loadProductWithParameters:params
                          completionBlock:^(BOOL result, NSError *error) {
                              if (result)
                              {
//                                  UIWindow *window = [UIApplication sharedApplication].keyWindow;
//                                  if (!window)
//                                  {
//                                      window = [[UIApplication sharedApplication].windows objectAtIndex:0];
//                                  }
//                                  [window addSubview:controller.view];
//                                  [self presentViewController:controller
//                                                     animated:YES
//                                                   completion:nil];
                              }
                          }];
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (!window)
    {
        window = [[UIApplication sharedApplication].windows objectAtIndex:0];
    }
    //[window addSubview:controller.view];
    [window.rootViewController presentViewController:controller animated:YES completion:nil];
    [controller autorelease];
    return true;
}

#pragma -
#pragma mark MBProgressHUDDelegate
- (void)hudWasHidden:(MBProgressHUD *)hud
{
    if (hud == progressHubComplete) {
        [progressHubComplete removeFromSuperview];
        [progressHubComplete release];
        progressHubComplete = nil;
    }
}

#pragma -
#pragma mark UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    m_ActionSheetRelBtn = buttonIndex;
    NUITools::NUITools_CompletetCallback();
}

#pragma -
#pragma mark SKStoreProductViewControllerDelegate
- (void)productViewControllerDidFinish:(SKStoreProductViewController *)viewController
{
    [viewController  dismissModalViewControllerAnimated:YES];
}

@end
